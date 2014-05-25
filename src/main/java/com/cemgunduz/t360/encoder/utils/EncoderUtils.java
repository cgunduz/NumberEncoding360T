package com.cemgunduz.t360.encoder.utils;

import java.util.List;
import java.util.Set;

/**
 * Created by cgunduz on 5/24/14.
 */
public interface EncoderUtils {

    /**
     * Takes a number and returns its encoding options as string array
     *
     * @param c
     * @return
     */
    Set<Character> numberToEncodableString(String c);

    /**
     * Finds and increments for each ignored characters
     *
     * @param phoneNumber
     * @param desiredIndex
     * @return
     */
    int getIndexAfterIgnoredCharactersForPhoneNumber(String phoneNumber, int desiredIndex);
    int getIndexAfterIgnoredCharactersForDictionaryItem(String dictionaryItem, int desiredIndex);

    /**
     * Searches for the specific character matches within the dictionary at the index defined
     *
     * @param characters
     * @param index
     * @return
     */
    List<String> getCandidateDictionarySublist(Set<Character> characters, int index, List<String> dictionary);

    /**
     * Returns the current dictionary matches for the given size
     *
     * @param dictionary
     * @param size
     * @return
     */
    List<String> getCurrentMatches(List<String> dictionary, int size);
}
