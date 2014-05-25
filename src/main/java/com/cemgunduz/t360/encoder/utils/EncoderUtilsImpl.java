package com.cemgunduz.t360.encoder.utils;

import com.cemgunduz.t360.encoder.entity.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by cgunduz on 5/24/14.
 */
public class EncoderUtilsImpl implements EncoderUtils {

    @Override
    public Set<Character> numberToEncodableString(String c) {

        return Constants.NUMERIC_LETTER_MAP.get(Integer.valueOf(c));
    }

    @Override
    public int getIndexAfterIgnoredCharactersForPhoneNumber(String phoneNumber, int desiredIndex) {

        return getIndexAfterIgnoredCharacters(phoneNumber, desiredIndex, Constants.IGNORED_PHONE_CHARACTERS);
    }

    @Override
    public int getIndexAfterIgnoredCharactersForDictionaryItem(String dictionaryItem, int desiredIndex) {

        return getIndexAfterIgnoredCharacters(dictionaryItem, desiredIndex, Constants.IGNORED_DICTIONARY_CHARACTERS);
    }

    // A cache or a different method to get candidate sublist would increase the performance greatly,
    // however space requirements tie my hands
    @Override
    public List<String> getCandidateDictionarySublist(Set<Character> characters, int index, List<String> dictionary) {

        List<String> candidateList = new ArrayList<String>();
        for(String candidate : dictionary)
        {
            if(index > candidate.length())
                continue;

            int processedIndex = getIndexAfterIgnoredCharactersForDictionaryItem(candidate,index);
            if(processedIndex < candidate.length() && characters.contains(candidate.charAt(processedIndex)))
                candidateList.add(candidate);
        }

        return candidateList;
    }

    @Override
    public List<String> getCurrentMatches(List<String> dictionary, int size) {

        List<String> matches = new ArrayList<String>();
        for(String candidate : dictionary)
        {
            int sizeAfterUmlaut = getIndexAfterIgnoredCharactersForDictionaryItem(candidate, size);
            if(sizeAfterUmlaut == candidate.length())
                matches.add(candidate);
        }

        return matches;
    }

    private int getIndexAfterIgnoredCharacters(String unprocessedString, int desiredIndex, Set<Character> excludedCharacters)
    {
        if(desiredIndex >= unprocessedString.length())
            return unprocessedString.length();

        int ignoredCharacterOccurence = 0;
        for(int i = 0; i <= desiredIndex; i++)
        {
            if(excludedCharacters.contains(unprocessedString.charAt(i)))
                ignoredCharacterOccurence++;
        }

        if(desiredIndex + ignoredCharacterOccurence >= unprocessedString.length())
            return unprocessedString.length();

        if(excludedCharacters.contains(unprocessedString.charAt(desiredIndex + ignoredCharacterOccurence )))
        {
            while(desiredIndex + ignoredCharacterOccurence < unprocessedString.length())
            {
                if(excludedCharacters.contains(unprocessedString.charAt(desiredIndex + ignoredCharacterOccurence )))
                    ignoredCharacterOccurence++;
                else
                    break;
            }
        }

        return desiredIndex + ignoredCharacterOccurence;
    }
}
