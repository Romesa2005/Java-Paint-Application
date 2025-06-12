package ca.utoronto.utm.paint;
public class OllamaPaint extends Ollama{
    public OllamaPaint(String host){
        super(host);
    }

    /**
     * Ask llama3 to generate a new Paint File based on the given prompt
     * @param prompt
     * @param outFileName name of new file to be created in users home directory
     */
    public void newFile(String prompt, String outFileName){
        String format = FileIO.readResourceFile("paintSaveFileExample.txt");

        String system = "The answer to this question should be a paint file. Respond only with a paint file and nothing else. "+format ;
        String response = this.call(system, prompt);
        FileIO.writeHomeFile(response, outFileName);    }

    /**
     * Ask llama3 to generate a new Paint File based on a modification of inFileName and the prompt
     * @param prompt the user supplied prompt
     * @param inFileName the Paint File Format file to be read and modified to outFileName
     * @param outFileName name of new file to be created in users home directory
     */
    public void modifyFile(String prompt, String inFileName, String outFileName){
        String format = FileIO.readResourceFile("paintSaveFileExample.txt");

        String system = "The answer to this question should be a paint file. Respond only with a paint file and nothing else. "+format ;
        String f = FileIO.readHomeFile(inFileName);
        String fullPrompt = "Produce a new paint file, resulting from the following OPERATION being performed on the following paint file. Only have positive coordinates.     OPERATION START"
                + prompt + " OPERATION END " + f;
        String response = this.call(system, fullPrompt);
        FileIO.writeHomeFile(response, outFileName);
    }

    /**
     * newFile1: DESCRIBE YOUR INTERESTING NEW PAINT FILE HERE
     * @param outFileName the name of the new file in the users home directory
     */
    @Override
    public void newFile1(String outFileName) {
        String prompt = "create a filled red filled rectangle. The p2 and p1 should be positive.";
        newFile(prompt, outFileName);    }

    /**
     * newFile2: DESCRIBE YOUR INTERESTING NEW PAINT FILE HERE
     * @param outFileName the name of the new file in the users home directory
     */
    @Override
    public void newFile2(String outFileName) {
        // YOUR CODE GOES HERE
        String prompt = "create a filled circle. in the centre. The centre should be positive";
        newFile(prompt, outFileName);    }

    /**
     * newFile3: DESCRIBE YOUR INTERESTING NEW PAINT FILE HERE
     * @param outFileName the name of the new file in the users home directory
     */
    @Override
    public void newFile3(String outFileName) {
        String prompt = "create a straight line with squiggle";
        newFile(prompt, outFileName);    }

    /**
     * modifyFile1: MODIFY inFileName TO PRODUCE outFileName BY ...
     * @param inFileName the name of the source file in the users home directory
     * @param outFileName the name of the new file in the users home directory
     */
    @Override
    public void modifyFile1(String inFileName, String outFileName) {
        String prompt = "add a outlined rectangle";
        modifyFile(prompt, inFileName, outFileName);    }

    /**
     * modifyFile2: MODIFY inFileName TO PRODUCE outFileName BY ...
     * @param inFileName the name of the source file in the users home directory
     * @param outFileName the name of the new file in the users home directory
     */
    @Override
    public void modifyFile2(String inFileName, String outFileName) {
        String prompt = "make a polyline with 5 points.";
        modifyFile(prompt, inFileName, outFileName);    }
    /**
     * modifyFile3: MODIFY inFileName TO PRODUCE outFileName BY ...
     * @param inFileName the name of the source file in the users home directory
     * @param outFileName the name of the new file in the users home directory
     */
    @Override
    public void modifyFile3(String inFileName, String outFileName) {
        String prompt = "add a outlined circle.";
        modifyFile(prompt, inFileName, outFileName);    }

    public static void main(String [] args){
        String prompt = null;

        prompt="Draw a 100 by 120 rectangle with 4 radius 5 circles at each rectangle corner.";
        OllamaPaint op = new OllamaPaint("dh2010pc26.utm.utoronto.ca"); // Replace this with your assigned Ollama server.

        prompt="Draw a 100 by 120 rectangle with 4 radius 5 circles at each rectangle corner.";
        op.newFile(prompt, "OllamaPaintFile1.txt");
        op.modifyFile("Remove all shapes except for the circles.","OllamaPaintFile1.txt", "OllamaPaintFile2.txt" );

        prompt="Draw 5 concentric circles with different colors.";
        op.newFile(prompt, "OllamaPaintFile3.txt");
        op.modifyFile("Change all circles into rectangles.", "OllamaPaintFile3.txt", "OllamaPaintFile4.txt" );

        prompt="Draw a polyline then two circles then a rectangle then 3 polylines all with different colors.";
        op.newFile(prompt, "OllamaPaintFile4.txt");

        prompt="Modify the following Paint Save File so that each circle is surrounded by a non-filled rectangle. ";
        op.modifyFile("Change all circles into rectangles.", "OllamaPaintFile4.txt", "OllamaPaintFile5.txt" );

        for(int i=1;i<=3;i++){
            op.newFile1("PaintFile1_"+i+".txt");
            op.newFile2("PaintFile2_"+i+".txt");
            op.newFile3("PaintFile3_"+i+".txt");
        }
        for(int i=1;i<=3;i++){
            for(int j=1;j<=3;j++) {
                op.modifyFile1("PaintFile"+ i +"_"+j+ ".txt", "PaintFile"+ i +"_"+j+"_1.txt");
                op.modifyFile2("PaintFile"+ i +"_"+j+ ".txt", "PaintFile"+ i +"_"+j+"_2.txt");
                op.modifyFile3("PaintFile"+ i +"_"+j+ ".txt", "PaintFile"+ i +"_"+j+"_3.txt");
            }
        }
    }
}
