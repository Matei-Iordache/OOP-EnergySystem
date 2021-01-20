package fileio;

import java.io.IOException;

/**
 * Factory pattern in witch an input will get parsed to java objects
 * or the java objects will be wrote in an output of json type
 */
public final class InputOutputFactory {

    private static InputOutputFactory instance = null;
    private String inputPath;
    private String outputPath;

    private InputOutputFactory() {

    }

    /**
     *
     * @param type type of action i want to do
     * @return an input loader or a writer
     * @throws IOException in case of exception
     */
    public InputOutput getInputOutput(final String type) throws IOException {
        switch (type) {
            case "input" -> {
                return new InputLoader(inputPath);
            }
            case "output" -> {
                return new Writer(outputPath);
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * method to make the factory a singleton
     * @return instance of the factory
     */
    public static InputOutputFactory getInstance() {
        if (instance == null) {
            instance = new InputOutputFactory();
        }
        return instance;
    }

    public void setInputPath(final String inputPath) {
        this.inputPath = inputPath;
    }

    public void setOutputPath(final String outputPath) {
        this.outputPath = outputPath;
    }
}
