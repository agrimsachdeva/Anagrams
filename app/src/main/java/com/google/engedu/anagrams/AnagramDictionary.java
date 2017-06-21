/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    ArrayList<String> wordList = new ArrayList<>();
    HashSet wordSet = new HashSet();
    HashMap<String, ArrayList<String>> lettersToWord = new HashMap();


    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            String sortedWord = sortLetters(word);
            if (lettersToWord.containsKey(sortedWord)) {
                lettersToWord.get(sortedWord).add(word);
            } else {
                ArrayList<String> anagrams = new ArrayList<>();
                anagrams.add(word);
                lettersToWord.put(sortedWord, anagrams);
            }
            wordList.add(word);
            wordSet.add(word);
        }
        Log.d("lettersToWord", lettersToWord.toString());
    }

    public boolean isGoodWord(String word, String base) {
        Boolean result = false;
        if (wordSet.contains(word) && (!word.contains(base))) {
            result = true;
        }
        Log.d("result boolean", result.toString());
        return result;
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();

        for (String word : wordList) {
            if ((word.length() == targetWord.length()) && sortLetters(word).equals(sortLetters(targetWord))) {
                result.add(word);
            }
        }
        Log.d("anagram list", result.toString());
        return result;
    }

    public String sortLetters(String word) {
        String sortedWord = "";
// put the characters into an array
        Character[] chars = new Character[word.length()];
        for (int i = 0; i < chars.length; i++)
            chars[i] = word.charAt(i);

// sort the array
        Arrays.sort(chars);

// rebuild the string
        StringBuilder sb = new StringBuilder(chars.length);
        for (char c : chars) sb.append(c);
        sortedWord = sb.toString();

        Log.d("sorted word", sortedWord);

        return sortedWord;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();

        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        for (Character letter : alphabet) {
            String newWord = word + letter.toString();
            String sortedWord = sortLetters(newWord);
            if (lettersToWord.containsKey(sortedWord)) {
                for (String temp : lettersToWord.get(sortedWord)) {
                    result.add(temp);
                }
            }
        }

        Log.d("anagram one more letter", result.toString());

        return result;
    }

    public String pickGoodStarterWord() {
        int rnd = random.nextInt(wordList.size());
        String word = wordList.get(rnd);

        while (!(lettersToWord.get(sortLetters(word)).size() >= 5)) {
            rnd ++;
            word = wordList.get(rnd);
        }

        return word;
    }
}
