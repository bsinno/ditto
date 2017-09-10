/*
 * Copyright (c) 2017 Bosch Software Innovations GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/epl-2.0/index.php
 *
 * Contributors:
 *    Bosch Software Innovations GmbH - initial contribution
 */
package org.eclipse.ditto.json;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.concurrent.Immutable;

/**
 * This class represents a JSON pointer consisting of at least one {@link JsonKey}.
 */
@Immutable
final class ImmutableJsonPointer implements JsonPointer {

    private static final String SLASH = "/";
    private static final String SLASH_REGEX = "/(?!/)"; // a SLASH which is not followed by another slash
    private static final ImmutableJsonPointer EMPTY = new ImmutableJsonPointer(Collections.emptyList());
    private static final Pattern PATTERN = Pattern.compile(SLASH_REGEX);

    private final List<JsonKey> jsonKeyHierarchy;

    private ImmutableJsonPointer(final List<JsonKey> theJsonKeys) {
        jsonKeyHierarchy = Collections.unmodifiableList(new ArrayList<>(theJsonKeys));
    }

    /**
     * Returns an empty instance of {@code ImmutableJsonPointer}.
     *
     * @return an empty JSON pointer.
     */
    public static ImmutableJsonPointer empty() {
        return EMPTY;
    }

    /**
     * Parses the given character sequence to obtain a new JSON pointer instance. This method is the inverse of
     * {@link ImmutableJsonPointer#toString()}.
     *
     * @param slashDelimitedCharSequence a character sequence representing a JSON pointer.
     * @return a new JSON pointer consisting of the JSON keys which were extracted from {@code
     * slashDelimitedCharSequence}.
     * @throws NullPointerException if {@code slashDelimitedCharSequence} is {@code null}.
     */
    public static JsonPointer ofParsed(final CharSequence slashDelimitedCharSequence) {
        requireNonNull(slashDelimitedCharSequence, "The JSON pointer character sequence must not be null!");

        if (slashDelimitedCharSequence instanceof JsonPointer) {
            return (JsonPointer) slashDelimitedCharSequence;
        } else if (0 == slashDelimitedCharSequence.length()) {
            return empty();
        }

        final List<JsonKey> jsonKeys = Stream.of(PATTERN.split(slashDelimitedCharSequence.toString()))
                .filter(keyName -> !keyName.isEmpty()) // ignore empty segments
                .filter(keyName -> !keyName.equals(SLASH)) // ignore "/" segments
                .map(ImmutableJsonKey::of)
                .collect(toList());

        return getAppropriatePointerImplementation(jsonKeys);
    }

    private static JsonPointer getAppropriatePointerImplementation(final List<JsonKey> jsonKeyHierarchy) {
        return new ImmutableJsonPointer(jsonKeyHierarchy);
    }

    /**
     * Returns a new JSON pointer instance.
     *
     * @param rootLevel the JSON key which is the root level of the JSON pointer to create.
     * @param subLevels the JSON keys which form sub levels to the root level as well as to each other.
     * @return a new JSON pointer consisting of hierarchical JSON keys.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public static JsonPointer of(final JsonKey rootLevel, final JsonKey... subLevels) {
        checkRootLevel(rootLevel);
        requireNonNull(subLevels, "The sub levels must not be null."
                + " If the JSON pointer does not require sub levels, just omit this argument.");

        final JsonPointer result;

        final List<JsonKey> keyHierarchy = new ArrayList<>(1 + subLevels.length);
        keyHierarchy.add(rootLevel);
        Collections.addAll(keyHierarchy, subLevels);

        result = new ImmutableJsonPointer(keyHierarchy);

        return result;
    }

    private static void checkRootLevel(final JsonKey rootLevel) {
        requireNonNull(rootLevel, "The root level of the JSON pointer must not be null!");
    }

    /**
     * Returns a new JSON pointer instance.
     *
     * @param rootLevel the JSON key which is the root level of the JSON pointer to create.
     * @param subPointer the JSON keys which form sub levels to the root level as well as to each other.
     * @return a new JSON pointer consisting of hierarchical JSON keys.
     * @throws NullPointerException if any argument is {@code null}.
     */
    static JsonPointer of(final JsonKey rootLevel, final JsonPointer subPointer) {
        checkRootLevel(rootLevel);
        checkSubPointer(subPointer);

        final JsonPointer result;

        final List<JsonKey> keyHierarchy = new ArrayList<>(1 + subPointer.getLevelCount());
        keyHierarchy.add(rootLevel);
        subPointer.forEach(keyHierarchy::add);

        result = new ImmutableJsonPointer(keyHierarchy);

        return result;
    }

    private static void checkSubPointer(final Object subPointer) {
        requireNonNull(subPointer, "The sub sub pointer to be appended must not be null!");
    }

    /**
     * Adds a level to this JSON pointer. For example, if this pointer is {@code "foo/bar"} and {@code addLevel()} is
     * called with a JSON field {@code "baz"} then the JSON pointer is {@code "foo/bar/baz"}.
     *
     * @param key the new level JSON field.
     * @return a <em>new</em> JSON pointer consisting of the old pointer extended by {@code jsonField}.
     * @throws NullPointerException if {@code jsonField} is {@code null}.
     */
    @Override
    public JsonPointer addLeaf(final JsonKey key) {
        requireNonNull(key, "The JSON field of the new level must not be null!");

        final List<JsonKey> newJsonKeys = new ArrayList<>(jsonKeyHierarchy);
        newJsonKeys.add(key);
        return new ImmutableJsonPointer(newJsonKeys);
    }

    @Override
    public JsonPointer append(final JsonPointer subPointer) {
        checkSubPointer(subPointer);

        final JsonPointer result;

        if (subPointer.isEmpty()) {
            result = this;
        } else {
            final List<JsonKey> newJsonKeys = new ArrayList<>(jsonKeyHierarchy);
            subPointer.forEach(newJsonKeys::add);
            result = new ImmutableJsonPointer(newJsonKeys);
        }

        return result;
    }

    /**
     * Returns the number of levels of this JSON pointer. For example if the pointer is {@code "foo/bar/baz"} this
     * method will return the value {@literal 3}.
     *
     * @return the number of levels of this pointer.
     */
    @Override
    public int getLevelCount() {
        return jsonKeyHierarchy.size();
    }

    @Override
    public boolean isEmpty() {
        return jsonKeyHierarchy.isEmpty();
    }

    @Override
    public Optional<JsonKey> get(final int level) {
        try {
            return Optional.of(jsonKeyHierarchy.get(level));
        } catch (final IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<JsonKey> getRoot() {
        return get(0);
    }

    @Override
    public Optional<JsonKey> getLeaf() {
        return get(getLevelCount() - 1);
    }

    @SuppressWarnings("squid:S1166")
    @Override
    public Optional<JsonPointer> getSubPointer(final int level) {
        final List<JsonKey> jsonKeysCopy = new ArrayList<>(jsonKeyHierarchy);
        try {
            final List<JsonKey> subList = jsonKeysCopy.subList(level, jsonKeysCopy.size());
            return Optional.of(getAppropriatePointerImplementation(subList));
        } catch (final IllegalArgumentException | IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<JsonPointer> getPrefixPointer(final int level) {
        final List<JsonKey> jsonKeysCopy = new ArrayList<>(jsonKeyHierarchy);

        try {
            final List<JsonKey> subList = jsonKeysCopy.subList(0, level);
            return Optional.of(getAppropriatePointerImplementation(subList));
        } catch (final IllegalArgumentException | IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    @Override
    public JsonPointer cutLeaf() {
        JsonPointer result = this;
        if (!isEmpty()) {
            final List<JsonKey> subList = jsonKeyHierarchy.subList(0, getLevelCount() - 1);
            result = getAppropriatePointerImplementation(subList);
        }
        return result;
    }

    @Override
    public JsonPointer nextLevel() {
        JsonPointer result = this;
        if (!isEmpty()) {
            result = getSubPointer(1).get();
        }
        return result;
    }

    @Override
    public JsonFieldSelector toFieldSelector() {
        return JsonFactory.newFieldSelector(this);
    }

    @Override
    public Iterator<JsonKey> iterator() {
        return new ArrayList<>(jsonKeyHierarchy).iterator();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ImmutableJsonPointer jsonKeys = (ImmutableJsonPointer) o;
        return Objects.equals(jsonKeyHierarchy, jsonKeys.jsonKeyHierarchy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jsonKeyHierarchy);
    }

    @Override
    public int length() {
        final String s = toString();
        return s.length();
    }

    @Override
    public char charAt(final int index) {
        final String s = toString();
        return s.charAt(index);
    }

    @Override
    public CharSequence subSequence(final int start, final int end) {
        final String s = toString();
        return s.subSequence(start, end);
    }

    /**
     * The string representation of this JSON pointer, i. e. all of its levels concatenated by {@literal "/"}. For
     * example if this pointer consists of the three levels {@code "foo"}, {@code "bar"} and {@code "baz"}, this method
     * will return the string {@code "/foo/bar/baz"}. If this pointer #isEmpty(), this method will return the empty
     * string {@code ""}.
     *
     * @return the string representation of this JSON pointer.
     */
    @Override
    public String toString() {
        final String stringRepresentation;
        if (jsonKeyHierarchy.isEmpty()) {
            stringRepresentation = SLASH;
        } else {
            stringRepresentation = SLASH + jsonKeyHierarchy.stream()
                    .map(JsonKey::toString)
                    .collect(Collectors.joining(SLASH));
        }
        return stringRepresentation;
    }

}
