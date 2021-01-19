/*
 * Created by babydeveloper on 1/17/21.
 *
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    public static String[] parseLineUsingRegex(String line) {

        // split the cell by commas, ignore commas in double quotes
        String[] lineAfterParse = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        for (int i = 0; i < lineAfterParse.length; i++) {
            // assign null to empty cell
//            if (lineAfterParse[i] == null || lineAfterParse[i].isEmpty()) {
//                lineAfterParse[i] = "null";
//            } else {
                // trim leading and tailing empty spaces
                // strip the quotes
                lineAfterParse[i] = lineAfterParse[i].trim()
                        .replaceAll("\"", "");
//            }
        }
        return lineAfterParse;
    }

    public static String[] parseLineUsingCode(String line) {
        List<String> res = new ArrayList<>();
        char[] lineChar = line.toCharArray();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        //0123
        //"Sd",  13,"Lunch"
        //     i
        for (int i = 0; i < lineChar.length; i++) {
            // check quote
            if(lineChar[i] == '\"'){
                inQuotes = !inQuotes;
            }
            // when not inQuote
            if(inQuotes == false){
                if(lineChar[i] == ','){      // finish one cell
                    // if the field is empty
                    if(sb.length()==0) {
                        String emptyCell = " ";
                        sb.append(emptyCell);
                    }
                    // add finished cell to res
                    res.add(sb.toString());
                    // flash sb
                    sb = new StringBuilder();
                }
                else if(i == lineChar.length-1){
                    if(lineChar[i] != '\"') {
                        sb.append(lineChar[i]);
                    }
                    // add finished cell to res
                    res.add(sb.toString());
                    // flash sb
                    sb = new StringBuilder();
                } else if(lineChar[i] == '\"' && lineChar[i+1] == ',' ||
                        lineChar[i] == '\"' && i == lineChar.length-1){
                    continue;
                } else{
                    sb.append(lineChar[i]);
                }
            }
            // when inQuote
            else if(inQuotes == true){
                if(lineChar[i] == '\"'){
                } else{
                    sb.append(lineChar[i]);
                }
            } else{
                sb.append(lineChar[i]);
            }
        }
        return removeSpaces(res);
    }


    private static String[] removeSpaces(List<String> res) {
        int end = 0;
        //[Sd,          13,        Lunch]   list of string -> tmpArr for each string elem
        // i
        for(int i = 0; i < res.size(); i++ ){           //         j
            char[] tmpArr = res.get(i).toCharArray(); // tmpArr: { 13448}
            char[] resArr = new char[tmpArr.length];
            for(int j = 0; j < tmpArr.length; j++){
                if (tmpArr[j] == ' ' && (j==0 || tmpArr[j-1] == ' ')){
                    continue;
                }
                resArr[end++] = tmpArr[j];
            }
            if(end>0 && tmpArr[end-1] == ' ') {
                String newString = new String(resArr,0,end-1);
                res.set(i,newString);
            } else {
                String newString = new String(resArr, 0, end);
                res.set(i, newString);
            }
            //reset
            resArr = null;
            end = 0;

        }

        String[] arrayToReturn = res.toArray(new String[0]);

        return arrayToReturn;
    }


//    public static void assignProperDataType(String line) {
//
////        List<String[]> records = new ArrayList<>();
////
////        for(String[] field: records) {
////            String name = field[0];
////            Integer storeNumber = Integer.parseInt(field[1].trim());
////            String features = field[2];
////            String phoneNumber = field[3];
////            //TODO: string -> parseToDateTime
////            String insertDate = field[4];
//////            String insertDate = parseToDateTime(field[4]);
////            if (field.length == 5) {
////                Starbucks sb2 = new Starbucks(name, storeNumber, features, phoneNumber, insertDate);
////                recordsAfterParsed.add(sb2);
////            } else {
////                String owner = field[5];
////                Starbucks sb1 = new Starbucks(name, storeNumber, features, phoneNumber, insertDate, owner);
////                recordsAfterParsed.add(sb1);
////            }
////        }
////        for(Object o: recordsAfterParsed)
////        System.out.println(o);
//
//    }
//    private static String parseToDateTime(String s) {
//
//    }

}



