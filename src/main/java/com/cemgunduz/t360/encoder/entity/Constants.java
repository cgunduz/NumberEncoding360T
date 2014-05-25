package com.cemgunduz.t360.encoder.entity;

import com.cemgunduz.utils.IoUtils;

import java.util.*;

/**
 * Created by cgunduz on 5/21/14.
 */
public class Constants {

    /*
    Basicly holds the static letter mapping information in the question :

    E | J N Q | R W X | D S Y | F T | A M | C I V | B K U | L O P | G H Z
    e | j n q | r w x | d s y | f t | a m | c i v | b k u | l o p | g h z
    0 |   1   |   2   |   3   |  4  |  5  |   6   |   7   |   8   |   9
    */
    public static final Map<Integer, Set<Character>> NUMERIC_LETTER_MAP;

    // Takes the dictionary values to memory as required for later use
    // Would have considered to store it in another way if the requirement was not stated as :
    // "It should also be memory efficient in that it does not use 75000 times 50 bytes for storing the dictionary"
    public static final List<String> DICTIONARY;

    // Contains '-' and '/'
    public static final Set<Character> IGNORED_PHONE_CHARACTERS;

    // Contains '"'
    public static final Set<Character> IGNORED_DICTIONARY_CHARACTERS;

    static
    {
        // Get letter numeric mapping input from file
        NUMERIC_LETTER_MAP = new HashMap<Integer, Set<Character>>();
        int number = 0;

        for(List<String> stringList : IoUtils.mapFileToStringList("input/letter_mapping.txt"))
        {
            Set<Character> characterSet = new HashSet<Character>();
            for(String s : stringList)
            {
                characterSet.add(s.charAt(0));
                characterSet.add(s.toLowerCase().charAt(0));
            }

            // Add each letter with the associated numeric value and increment
            NUMERIC_LETTER_MAP.put(number, characterSet);
            number++;
        }

        // Get dictionary list from file
        DICTIONARY = new ArrayList<String>();
        for(List<String> stringList : IoUtils.mapFileToStringList("input/dictionary.txt"))
        {
            for(String s : stringList)
            {
                DICTIONARY.add(s);
            }
        }

        // Set ignored phone characters, just two so add them by hand
        IGNORED_PHONE_CHARACTERS = new HashSet<Character>();
        IGNORED_PHONE_CHARACTERS.add('/');
        IGNORED_PHONE_CHARACTERS.add('-');

        // Set ignored dictionary characters, just one so add them by hand
        IGNORED_DICTIONARY_CHARACTERS = new HashSet<Character>();
        IGNORED_DICTIONARY_CHARACTERS.add('\"');
    }
}
