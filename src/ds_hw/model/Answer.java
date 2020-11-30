package ds_hw.model;

public class Answer {
    public String name;
    public double rate;
    public int depth;

    public Answer(String name, double rate, int depth) {
        this.name = name;
        this.rate = rate;
        this.depth = depth;
    }

    public String getName() {
        return name;
    }

    public int getDepth() {
        return depth;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(name);
        builder.append(" ");
        builder.append(String.format("%.6f", rate));
        builder.append(" ");
        for (int i = 0; i < depth; i++) {
            builder.append("+");
        }
        return builder.toString();
    }
}

