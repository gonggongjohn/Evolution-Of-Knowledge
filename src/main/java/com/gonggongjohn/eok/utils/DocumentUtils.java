package com.gonggongjohn.eok.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Stack;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class DocumentUtils {
    public ArrayList<String> readDocument(Minecraft mc, ResourceLocation location){
        ArrayList<String> document = new ArrayList<String>();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(mc.getResourceManager().getResource(location).getInputStream(), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while(line != null){
                document.add(line);
                line = bufferedReader.readLine();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return document;
    }

    public String analyzeLine(String line){
        int cur = 0;
        Stack<SymbolAndCur> symbolStack = new Stack<SymbolAndCur>();
        StringBuilder result = new StringBuilder();
        while(cur < line.length()){
            if(line.charAt(cur) == '\t'){
                result.append("    ");

            }
            if(line.charAt(cur) == '*'){
                if(line.charAt(cur + 1) == '*'){
                    if(symbolStack.peek().getSymbolID() == 1){
                        SymbolAndCur symbolTop = symbolStack.pop();
                        result.append(TextFormatting.BOLD).append(line, symbolTop.getCur(), cur - 1);
                    }
                    else{
                        symbolStack.push(new SymbolAndCur(1, cur + 2));
                    }
                }
                else{
                    if(symbolStack.peek().getSymbolID() == 1){
                        SymbolAndCur symbolTop = symbolStack.pop();
                        result.append(TextFormatting.ITALIC).append(line, symbolTop.getCur(), cur - 1);
                    }
                    else{
                        symbolStack.push(new SymbolAndCur(2, cur + 1));
                    }
                }
            }
            if(symbolStack.isEmpty()) result.append(line.charAt(cur));
            cur ++;
        }
        return result.toString();
    }

    public static class SymbolAndCur{
        /**
         * symbolID, 1 is **, 2 is *
         */
        private int symbolID;
        private int cur;
        public SymbolAndCur(int symbolID, int cur){
            this.symbolID = symbolID;
            this.cur = cur;
        }

        public int getSymbolID(){
            return this.symbolID;
        }

        public int getCur() {
            return this.cur;
        }
    }
}
