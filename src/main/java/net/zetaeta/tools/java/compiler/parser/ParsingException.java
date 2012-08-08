package net.zetaeta.tools.java.compiler.parser;

import net.zetaeta.tools.java.compiler.CompilationException;

public class ParsingException extends CompilationException {
    public ParsingException() {
        
    }
    
    public ParsingException(String message) {
        super(message);
    }
    
    public ParsingException(Throwable cause) {
        super(cause);
    }
    
    public ParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
