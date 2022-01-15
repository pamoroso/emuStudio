package net.emustudio.plugins.compiler.as8080.visitors;

import net.emustudio.emulib.runtime.io.IntelHEX;
import net.emustudio.plugins.compiler.as8080.ast.Evaluated;
import net.emustudio.plugins.compiler.as8080.ast.NodeVisitor;
import net.emustudio.plugins.compiler.as8080.ast.data.DataDB;
import net.emustudio.plugins.compiler.as8080.ast.data.DataDS;
import net.emustudio.plugins.compiler.as8080.ast.data.DataDW;
import net.emustudio.plugins.compiler.as8080.ast.instr.*;
import net.emustudio.plugins.compiler.as8080.ast.pseudo.PseudoOrg;

import java.util.Objects;

import static net.emustudio.plugins.compiler.as8080.CompileError.valueMustBePositive;

public class GenerateCodeVisitor extends NodeVisitor {
    private final IntelHEX hex;
    private int expectedBytes;

    public GenerateCodeVisitor(IntelHEX hex) {
        this.hex = Objects.requireNonNull(hex);
    }

    @Override
    public void visit(DataDB node) {
        expectedBytes = 1;
        visitChildren(node);
    }

    @Override
    public void visit(DataDW node) {
        expectedBytes = 2;
        visitChildren(node);
    }

    @Override
    public void visit(DataDS node) {
        node.collectChild(Evaluated.class)
            .ifPresent(e -> {
                if (e.value < 0) {
                    error(valueMustBePositive(e));
                } else {
                    for (int i = 0; i < e.value; i++) {
                        hex.add((byte) 0);
                    }
                }
            });
    }

    @Override
    public void visit(InstrExpr node) {
        hex.add(node.eval());
        expectedBytes = node.getExprSizeBytes();
        visitChildren(node);
    }

    @Override
    public void visit(InstrNoArgs node) {
        hex.add(node.eval());
    }

    @Override
    public void visit(InstrReg node) {
        hex.add(node.eval());
    }

    @Override
    public void visit(InstrRegExpr node) {
        hex.add(node.eval());
        expectedBytes = 1;
        visitChildren(node);
    }

    @Override
    public void visit(InstrRegPair node) {
        hex.add(node.eval());
    }

    @Override
    public void visit(InstrRegPairExpr node) {
        hex.add(node.eval());
        expectedBytes = 2;
        visitChildren(node);
    }

    @Override
    public void visit(InstrRegReg node) {
        hex.add(node.eval());
    }

    @Override
    public void visit(PseudoOrg node) {
        node.collectChild(Evaluated.class)
            .ifPresent(e -> {
                if (e.value < 0) {
                    error(valueMustBePositive(node));
                } else {
                    hex.setNextAddress(e.value);
                }
            });
    }

    @Override
    public void visit(Evaluated node) {
        if (expectedBytes == 1) {
            hex.add((byte) (node.value & 0xFF));
        } else if (expectedBytes == 2) {
            byte byte0 = (byte) (node.value & 0xFF);
            byte byte1 = (byte) (node.value >>> 8);

            hex.add(byte0);
            hex.add(byte1);
        }
    }
}
