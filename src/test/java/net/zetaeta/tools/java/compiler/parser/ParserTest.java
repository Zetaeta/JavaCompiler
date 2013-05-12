package net.zetaeta.tools.java.compiler.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ParserTest {

    public static void main(String[] args) throws IOException, ParsingException {
        BufferedReader in;
        if (args.length > 0) {
            in = new BufferedReader(new FileReader(args[0]));
        }
        else {
            in = new BufferedReader(new InputStreamReader(System.in));
        }
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = in.readLine()) != null) {
            sb.append(line).append('\n');
        }
        Lexer lexer = new Lexer(sb.toString());
        Parser parser = new Parser(lexer);
        System.out.println(parser.parseCompilationUnit());
    }
}
