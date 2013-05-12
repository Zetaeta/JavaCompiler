package net.zetaeta.tools.java.compiler.ast;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;

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
    
    public int getModifiers() {
        return mods;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (hasFlag(PUBLIC)) {
            sb.append("public").append(' ');
        }
        if (hasFlag(PROTECTED)) {
            sb.append("protected").append(' ');
        }
        if (hasFlag(PRIVATE)) {
            sb.append("private").append(' ');
        }
        if (hasFlag(STATIC)) {
            sb.append("static").append(' ');
        }
        if (hasFlag(FINAL)) {
            sb.append("final").append(' ');
        }
        if (hasFlag(SYNCHRONIZED)) {
            sb.append("synchronized").append(' ');
        }
        if (hasFlag(VOLATILE)) {
            sb.append("volatile").append(' ');
        }
        if (hasFlag(TRANSIENT)) {
            sb.append("transient").append(' ');
        }
        if (hasFlag(NATIVE)) {
            sb.append("native").append(' ');
        }
        if (hasFlag(ABSTRACT)) {
            sb.append("abstract").append(' ');
        }
        if (hasFlag(STRICTFP)) {
            sb.append("strictfp").append(' ');
        }
        if (hasFlag(CLASS)) {
            sb.append("class").append(' ');
        }
        if (hasFlag(ENUM)) {
            sb.append("enum").append(' ');
        }
        if (hasFlag(INTERFACE)) {
            sb.append("interface").append(' ');
        }
        if (hasFlag(ANNOTATION)) {
            sb.append("@interface").append(' ');
        }
        return sb.toString();
    }
}
