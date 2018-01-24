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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class TrieNode {
    private HashMap<String, TrieNode> children;
    private boolean isWord; //end of identifier

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {
        TrieNode currentNode=this;
        int i;
        for(i=0;i<s.length();i++)
        {
            if(!currentNode.children.containsKey(String.valueOf(s.charAt(i))))
            {
                currentNode.children.put(String.valueOf(s.charAt(i)),new TrieNode());
            }
            currentNode=currentNode.children.get(String.valueOf(s.charAt(i)));
        }
        currentNode.isWord=true;
    }

    public boolean isWord(String s) {
        TrieNode currentNode=this;
        int i;
        for(i=0;i<s.length();i++)
        {
            if(!currentNode.children.containsKey(String.valueOf(s.charAt(i))))
            {
                return false;
            }
            currentNode=currentNode.children.get(String.valueOf(s.charAt(i)));
        }
        return currentNode.isWord;
    }

    public String getAnyWordStartingWith(String s)
    {
        String ans="";
        TrieNode currentNode=this;

        char ch1='a';
        for(int i=0;i<s.length();i++)
        {
            Log.e("current node1: ",String.valueOf(s.charAt(i)));
            if(!currentNode.children.containsKey(String.valueOf(s.charAt(i))))
            {
                Log.e("current node2: ",String.valueOf(s.charAt(i)));
                return null;

            }
            ans+=s.charAt(i);
            currentNode=currentNode.children.get(String.valueOf(s.charAt(i)));
        }
        //Log.e("current node3: ",String.valueOf(s.charAt(i)));
        if(currentNode.children.size()==0) {
            Log.e("current noe4:ans ", ans);
            return null;
        }
        Log.e("ans ",ans);
        for(ch1='a';ch1<='z';ch1++) {
            if (currentNode.children.containsKey(String.valueOf(ch1))) {
                ans+=String.valueOf(ch1);
                if(currentNode.isWord)
                    return ans;
                currentNode=currentNode.children.get(String.valueOf(ch1));
                Log.e("ans111",ans);
                ch1='a';
            }
            /*
            else if(ch1=='z')
                return null;*/

        }
        /*
        do{
            for(char i='a';i<='z';i++)
            {
                if(currentNode.children.containsKey(String.valueOf(i)))
                {
                    ans+=i;
                    currentNode=currentNode.children.get(String.valueOf(i));
                    break;
                }
            }
        }while (!currentNode.isWord);*/
        return ans;
    }

    public String getGoodWordStartingWith(String s) {

        String ans="";
        TrieNode currentNodep=this;
        ArrayList<String> options=new ArrayList<String>();
        char ch1;
        for(int i=0;i<s.length();i++)
        {
            if(!currentNodep.children.containsKey(String.valueOf(s.charAt(i))))
            {
                Log.e("s.charAt(i) for which no word exists: ",String.valueOf(s.charAt(i)));
                return null;
            }
            ans+=s.charAt(i);
            currentNodep=currentNodep.children.get(String.valueOf(s.charAt(i)));
        }
        while (currentNodep.children.size()!=0) {
            if (currentNodep.children.size() == 0) {
                return null;
            }
            for (ch1 = 'a'; ch1 <= 'z'; ch1++) {
                if (currentNodep.children.containsKey(String.valueOf(ch1))) {
                    options.add(String.valueOf(ch1));
                }
            }
            Log.e("Options",options.toString());
            if(options.size()==0)
                return null;
            int rn = new Random().nextInt(options.size());
            String atemp = options.get(rn);
            ans += atemp;
            /*if (currentNodep.isWord) {
                boolean rnd = new Random().nextBoolean();
                if (rnd)
                    return ans;
            }*/
           options.clear();
            currentNodep = currentNodep.children.get(String.valueOf(atemp));
        }
        return ans;
    }
}
