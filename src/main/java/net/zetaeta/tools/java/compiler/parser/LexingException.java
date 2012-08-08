package net.zetaeta.tools.java.compiler.parser;

import net.zetaeta.tools.java.compiler.CompilationException;

public class LexingException extends CompilationException {
    public LexingException() {
        
    }
    
    public LexingException(String message) {
        super(message);
    }
    
    public LexingException(Throwable cause) {
        super(cause);
    }
    
    public LexingException(String message, Throwable cause) {
        super(message, cause);
    }
}
