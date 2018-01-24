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

package com.google.engedu.ghost;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {

    private ArrayList<String> words;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {

        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        int m,low=0,high=words.size()-1;
        String tempWord;
        String ans=null;
        while (low<high)
        {
            m=(low+high)/2;
            tempWord=words.get(m);
            if(tempWord.length()>prefix.length())
                tempWord=tempWord.substring(0,prefix.length());
            if(tempWord.compareToIgnoreCase(prefix)==0)
            {
                ans=words.get(m);
                break;
            }
            else if(tempWord.compareToIgnoreCase(prefix)>0)
            {
                high=m-1;
            }
            else {
                low=m+1;
            }
        }
        return ans;
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        //String selected = null;
        //return selected;
        int rn;
        boolean found=false;
        if(prefix.length()==0)
        {
            rn=new Random().nextInt(words.size());
            return words.get(rn);
        }
        int m=0,low=0,high=words.size()-1;
        ArrayList<String> options=new ArrayList<>();
        String tempWord;
        String ans=null;
        while (low<high)
        {
            m=(low+high)/2;
            tempWord=words.get(m);
            if(tempWord.length()>prefix.length())
                tempWord=tempWord.substring(0,prefix.length());
            Log.e("tempWord",tempWord);
            if(tempWord.compareToIgnoreCase(prefix)==0)
            {
                ans=words.get(m);
                Log.e("ans",ans);
                found=true;
                break;
            }
            else if(tempWord.compareToIgnoreCase(prefix)>0)
            {
                high=m-1;
            }
            else {
                low=m+1;
            }
        }
        if(!found)
            return null;
        int l=-1,m1=m;
        Log.e("before options",ans);
        while(ans.startsWith(prefix))
        {
            options.add(ans);
            m1++;
            l++;
            ans=words.get(m1);
            Log.e("in loop options",ans);
        }
        ans=words.get(m-1);
        while(ans.startsWith(prefix))
        {
            options.add(ans);
            m--;
            l++;
            ans=words.get(m);
            Log.e("in second loop", ans);
        }
        rn=new Random().nextInt(l+1);

        Log.e("random no=", "randnum");
        //printing the whole arraylist
        Log.e("options:",options.toString());
        ans=options.get(rn);
        return ans;
    }
}