package net.emustudio.plugins.compiler.as8080.ast.pseudo;

import net.emustudio.plugins.compiler.as8080.ast.Node;
import net.emustudio.plugins.compiler.as8080.ast.NodeVisitor;
import org.antlr.v4.runtime.Token;

import java.util.Objects;

public class PseudoEqu extends Node {
    public final String id;

    public PseudoEqu(int line, int column, String id) {
        super(line, column);
        this.id = Objects.requireNonNull(id);
        // expr is the only child
    }

    public PseudoEqu(Token id) {
        this(id.getLine(), id.getCharPositionInLine(), id.getText());
    }

    @Override
    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }
}
