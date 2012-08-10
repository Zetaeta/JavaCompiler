package net.zetaeta.tools.java.compiler.ast;

import java.util.ArrayList;
import java.util.List;

public class Modifiers {
    private int mods;
    
    private List<Annotation> annotations = new ArrayList<>();
    
    public static final int PUBLIC       = 0x0001,
                            PRIVATE      = 0x0002,
                            PROTECTED    = 0x0004,
                            STATIC       = 0x0008,
                            FINAL        = 0x0010,
                            SYNCHRONIZED = 0x0020,
                            VOLATILE     = 0x0040,
                            TRANSIENT    = 0x0080,
                            NATIVE       = 0x0100,
                            INTERFACE    = 0x0200,
                            ABSTRACT     = 0x0400,
                            STRICTFP     = 0x0800,
                            ANNOTATION   = 0x2000,
                            ENUM         = 0x4000,
                            CLASS        = 0x8000;
    
    public void addFlag(int flag) {
        mods |= flag;
    }
    
    public void removeFlag(int flag) {
        mods &= ~flag;
    }
    
    public boolean hasFlag(int flag) {
        return (mods & flag) > 0;
    }
    
    public void addAnnotation(Annotation a) {
        annotations.add(a);
    }
}
