package net.zetaeta.tools.java.compiler.parser;

import java.util.HashMap;
import java.util.Map;

import net.zetaeta.tools.java.compiler.parser.Token.Type;

public class Keywords {
    
    private static Map<String, Token.Type> table;
    
    static {
        table = new HashMap<String, Token.Type>();
        initialiseTable();
    }
    
    public static boolean isKeyword(String ident) {
        return table.containsKey(ident);
    }
    
    public static Token.Type lookup(String ident) {
        Type result;
        if (table.containsKey(ident)) {
            result = table.get(ident);
        }
        else {
            result = Token.Type.IDENTIFIER;
        }
        return result;
    }
    
    private static void initialiseTable() {
        for (int index = Type.ABSTRACT.ordinal(); index <= Type.WHILE.ordinal(); ++index) {
            Type type = Type.values()[index];
            table.put(type.getName(), type);
        }
    }
}
