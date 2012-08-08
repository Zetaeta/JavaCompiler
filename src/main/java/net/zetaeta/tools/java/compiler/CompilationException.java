package net.zetaeta.tools.java.compiler;

public class CompilationException extends Exception {
    public CompilationException() {
        
    }
    
    public CompilationException(String message) {
        super(message);
    }
    
    public CompilationException(Throwable cause) {
        super(cause);
    }
    
    public CompilationException(String message, Throwable cause) {
        super(message, cause);
    }
}
