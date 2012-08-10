package net.zetaeta.tools.java.compiler.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LexerTest {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line = in.readLine();
        Lexer lexer = new Lexer(line);
        while (lexer.nextToken().getType() != Token.Type.EOF) {
            System.out.println(lexer.token() + " ");
//            System.out.flush();
        }
    }
}
