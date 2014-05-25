package com.cemgunduz.t360.encoder;

import com.cemgunduz.t360.encoder.entity.Constants;
import com.cemgunduz.t360.encoder.utils.EncoderUtils;
import com.cemgunduz.t360.encoder.utils.EncoderUtilsImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by cgunduz on 5/21/14.
 *
 * Encodes a phone number by using the mappings and the dictionary
 */
public class Encoder {

    private EncoderUtils encoderUtils = new EncoderUtilsImpl();

    /**
     * The only publicly available method of Encoder class, which performs the operations stated in the question
     *
     * @param phoneNumber
     * @return
     */
    public List<String> encodePhoneNumber(String phoneNumber)
    {
        // Consider making a method for copying the original dictionary and calling it everywhere where the original call is made
        return encodePhoneNumber(phoneNumber, false, Constants.DICTIONARY);
    }

    // Makes recursive calls with options hence seperated from the publicy available version
    // The idea is to be able to open up new public methods with no change on the main logic such as
    // not starting to accept single numbers or using a different dictionary
    private List<String> encodePhoneNumber(String phoneNumber, boolean hasParalelNumber, List<String> candidateDictionarySubList)
    {
        // Answer here
        List<String> encodingOptions = new ArrayList<String>();
        if(phoneNumber.length() == 0)
            return encodingOptions;

        // TODO : is it necessary ? Take a copy
        List<String> candidateDictionarySubListCopy = new ArrayList<String>();
        candidateDictionarySubListCopy.addAll(candidateDictionarySubList);

        int index = 0;
        int indexAfterCleanUp = encoderUtils.getIndexAfterIgnoredCharactersForPhoneNumber(phoneNumber, index);
        while(phoneNumber.length() > indexAfterCleanUp)
        {
            // Get the encodable character candidates
            Set<Character> encodableList = encoderUtils.numberToEncodableString(phoneNumber.substring(indexAfterCleanUp,indexAfterCleanUp+1));

            // Get the fresh candidate dictionary sub list
            // Each call makes the list smaller, leading to a smaller comparison effort
            candidateDictionarySubListCopy = encoderUtils.getCandidateDictionarySublist(encodableList,index,candidateDictionarySubListCopy);

            // Every match results on a recursive call for the smaller version of the phone number,
            // all the results add up eventually to build all combinations
            List<String> matches = encoderUtils.getCurrentMatches(candidateDictionarySubListCopy, index + 1);
            if(matches.size() > 0)
            {
                String unprocessedPartOfPhoneNumber = phoneNumber.substring(
                        encoderUtils.getIndexAfterIgnoredCharactersForPhoneNumber(phoneNumber, index + 1)
                        , phoneNumber.length());

                if(unprocessedPartOfPhoneNumber.length() == 0)
                {
                    encodingOptions.addAll(matches);
                    return encodingOptions;
                }

                List<String> subMatches = encodePhoneNumber(unprocessedPartOfPhoneNumber, false, Constants.DICTIONARY);

                for(String match : matches)
                    encodingOptions.addAll(appendResult(match + " ", subMatches));
            }

            // If all out of candidates, no point on trying anymore
            if(candidateDictionarySubListCopy.size() < 1)
            {
                if(!hasParalelNumber && encodingOptions.size() == 0)
                {
                    encodingOptions.addAll(appendResult(phoneNumber.substring(0,1) + " ",
                            encodePhoneNumber(phoneNumber.substring(1, phoneNumber.length()), true, Constants.DICTIONARY)));
                } 

                return encodingOptions;
            }

            // Need to track ignored characters, similar with umlaut, due to space requirement
            // Otherwise I would have precomputed them
            index++;
            indexAfterCleanUp = encoderUtils.getIndexAfterIgnoredCharactersForPhoneNumber(phoneNumber, index);
        }

        // Corner case single character encoding and usage of numbers in general
        if(!hasParalelNumber && encodingOptions.size() == 0)
        {
            if(phoneNumber.length() == 1)
                return Arrays.asList(phoneNumber.substring(0,1));
            else
            {
                encodingOptions.addAll(appendResult(phoneNumber.substring(0,1) + " ",
                        encodePhoneNumber(phoneNumber.substring(1,phoneNumber.length()), true, Constants.DICTIONARY)));
            }

        }

        return encodingOptions;
    }

    // Simple helper method which creates a new list and appends the post result on top of the head
    private List<String> appendResult(String head, List<String> applicableCandidates)
    {
        List<String> result = new ArrayList<String>();

        if(applicableCandidates.size() == 0)
            return result;
        else
            for(String applicableCandidate : applicableCandidates)
                result.add(head + applicableCandidate);

        return result;
    }
}
