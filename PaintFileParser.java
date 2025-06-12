package ca.utoronto.utm.paint;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.paint.Color;
/**
 * Parse a file in Version 1.0 PaintSaveFile format. An instance of this class
 * understands the paint save file format, storing information about
 * its effort to parse a file. After a successful parse, an instance
 * will have an ArrayList of PaintCommand suitable for rendering.
 * If there is an error in the parse, the instance stores information
 * about the error. For more on the format of Version 1.0 of the paint 
 * save file format, see the associated documentation.
 * 
 * @author 
 *
 */
public class PaintFileParser {
	private int lineNumber = 0; // the current line being parsed
	private String errorMessage =""; // error encountered during parse
	private PaintModel paintModel;

	/**
	 // Patterns for recognizing the start and end of the entire file and each shape
	 */
	private Pattern pFileStart=Pattern.compile("^PaintSaveFileVersion1.0$");
	private Pattern pFileEnd=Pattern.compile("^EndPaintSaveFile$");

	private Pattern pCircleStart=Pattern.compile("^Circle$");
	private Pattern pCircleEnd=Pattern.compile("^EndCircle$");

	private Pattern pRectangleStart=Pattern.compile("^Rectangle$");
	private Pattern pRectangleEnd=Pattern.compile("^EndRectangle$");

	private Pattern pSquiggleStart=Pattern.compile("^Squiggle$");
	private Pattern pSquiggleEnd=Pattern.compile("^EndSquiggle$");

	private Pattern pPolylineStart=Pattern.compile("^Polyline$");
	private Pattern pPolylineEnd=Pattern.compile("^EndPolyline$");
	// ADD MORE!!

	/**
	 * Store an appropriate error message in this, including
	 * lineNumber where the error occurred.
	 * @param mesg
	 */
	private void error(String mesg){
		this.errorMessage = "Error in line "+lineNumber+" "+mesg;
	}

	/**
	 *
	 * @return the error message resulting from an unsuccessful parse
	 */
	public String getErrorMessage(){
		return this.errorMessage;
	}

	/**
	 * Parse the specified file
	 * @param fileName
	 * @return
	 */
	public boolean parse(String fileName){
		boolean retVal = false;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
			PaintModel pm = new PaintModel();
			retVal = this.parse(br, pm);
		} catch (FileNotFoundException e) {
			error("File Not Found: "+fileName);
		} finally {
			try { br.close(); } catch (Exception e){};
		}
		return retVal;
	}

	/**
	 * Parse the specified inputStream as a Paint Save File Format file.
	 * @param inputStream
	 * @return
	 */
	public boolean parse(BufferedReader inputStream){
		PaintModel pm = new PaintModel();
		return this.parse(inputStream, pm);
	}

	/**
	 * Parse the inputStream as a Paint Save File Format file.
	 * The result of the parse is stored as an ArrayList of Paint command.
	 * If the parse was not successful, this.errorMessage is appropriately
	 * set, with a useful error message.
	 *
	 * @param inputStream the open file to parse
	 * @param paintModel the paint model to add the commands to
	 * @return whether the complete file was successfully parsed
	 */
	public boolean parse(BufferedReader inputStream, PaintModel paintModel) {
		this.paintModel = paintModel;
		this.errorMessage="";

		// Initialize shape command variables

		CircleCommand circleCommand = null;
		RectangleCommand rectangleCommand = null;
		SquiggleCommand squiggleCommand = null;
		PolylineCommand polylineCommand = null;
		boolean retVal = true;
		try {
			int state=0; Matcher m; String l;

			this.lineNumber=0;
			while ((l = inputStream.readLine()) != null) {
				this.lineNumber++;
				//System.out.println(lineNumber+" "+l+" "+state);
				l=l.replaceAll("\\s+",""); // strip all whitespace
				if (!l.equals("")){
					// Patterns for matching shape attributes
					Pattern pcolor = Pattern.compile("^color:(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?),(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?),(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
					Pattern pfill = Pattern.compile("^filled:(true|false)$");
					Pattern pcenter= Pattern.compile("^center:\\(([1-9][0-9]?|[1-9][0-9]?|1[0-9]{2}|2[0-9]{2}|3[0-9]{2}|4[0-9]{2}|5[0-5][0-9]|560),([1-9]|[1-9][0-9]|6[0-0-1][0-3]|[1-5][0-9]{2}|6[0-9]{2}|613)\\)$");
					Pattern pradius= Pattern.compile("^radius:([0-9]|[1-9][0-9]|[1-7][0-9]{2}|8[0-2][0-9]|830)$");
					Pattern pp1= Pattern.compile("^p1:\\(([1-9][0-9]?|[1-9][0-9]?|1[0-9]{2}|2[0-9]{2}|3[0-9]{2}|4[0-9]{2}|5[0-5][0-9]|560),([1-9]|[1-9][0-9]|6[0-0-1][0-3]|[1-5][0-9]{2}|6[0-9]{2}|613)\\)$");
					Pattern pp2= Pattern.compile("^p2:\\(([1-9][0-9]?|[1-9][0-9]?|1[0-9]{2}|2[0-9]{2}|3[0-9]{2}|4[0-9]{2}|5[0-5][0-9]|560),([1-9]|[1-9][0-9]|6[0-0-1][0-3]|[1-5][0-9]{2}|6[0-9]{2}|613)\\)$");
					Pattern ppoint= Pattern.compile("^point:\\(([1-9][0-9]?|[1-9][0-9]?|1[0-9]{2}|2[0-9]{2}|3[0-9]{2}|4[0-9]{2}|5[0-5][0-9]|560),([1-9]|[1-9][0-9]|6[0-0-1][0-3]|[1-5][0-9]{2}|6[0-9]{2}|613)\\)$");

					// State machine that parses the file structure and each shape type
					switch(state){
					case 0:
						// Expecting file header

						m=pFileStart.matcher(l);
						if(m.matches()){
							state=1;
							break;
						}
						error("Expected Start of Paint Save File");
						retVal= false;
						break;
					case 1: // Looking for the start of a new object or end of the save file
						m=pCircleStart.matcher(l);
						if(m.matches()){
							circleCommand= new CircleCommand(new Point(0,0),0);
							state=2;
							break;
						}
						else if (pRectangleStart.matcher(l).matches()){
							rectangleCommand = new RectangleCommand(new Point(0,0),new Point(0,0));
							state = 7;
							break;
						}
						else if (pSquiggleStart.matcher(l).matches()){
							squiggleCommand = new SquiggleCommand();
							state = 12;
							break;
						}
						else if (pPolylineStart.matcher(l).matches()){
							polylineCommand = new PolylineCommand();
							state = 17;
							break;
						}
						else if (pFileEnd.matcher(l).matches()){
							state=22;
							retVal=true;
							break;
						}
						else{error("Expected Start of Shape or End Paint Save File");}
					case 2: // Parse Circle Color
						if (pcolor.matcher(l).matches()){
							int red = Integer.parseInt(l.substring(6,l.indexOf(',')));
							int blue = Integer.parseInt(l.substring(l.indexOf(',')+1,l.indexOf(',',l.indexOf(',')+1)));
							int green =  Integer.parseInt(l.substring(l.indexOf(',',l.indexOf(',')+1)+1));
							Color color = null;
							circleCommand.setColor( Color.rgb(red,blue,green));
							state =3;
							}
						else{error("Expected Circle color");
							return false;
						}
						break;
					case 3: // Parse Circle filled
						if (pfill.matcher(l).matches()){
							String bool = l.substring(7);
							if (bool.equals("true")){
								circleCommand.setFill(true);}
							else if (bool.equals("false")){
								circleCommand.setFill(false);}
							state=4;
						}
						else{error("Expected Circle filled");
							return false;}
						break;
					case 4:  // Parse Circle center
						if (pcenter.matcher(l).matches()){
							int xcenter = Integer.parseInt(l.substring(8,l.indexOf(',')));
							int ycenter = Integer.parseInt(l.substring(l.indexOf(',')+1,l.length()-1));
							circleCommand.setCentre(new Point(xcenter,ycenter));
							state =5;
						}
						else{error("Expected Circle center");
							return false;}
						break;

					case 5:  // Parse Circle radius
						if (pradius.matcher(l).matches()){
						int radius = Integer.parseInt(l.substring(7));
						circleCommand.setRadius(radius);
						state =6;}
						else {error("Expected Circle Radius");
							return false;}

						break;
					case 6: // Parse Circle end
						if (pCircleEnd.matcher(l).matches()){
							this.paintModel.addCommand(circleCommand);
							circleCommand=null;
							state=1;
						}
						else{error("Expected End Circle");
							return false;}
						break;
					case 7: // Rectangle color
						if (pcolor.matcher(l).matches()){
							int red = Integer.parseInt(l.substring(6,l.indexOf(',')));
							int blue = Integer.parseInt(l.substring(l.indexOf(',')+1,l.indexOf(',',l.indexOf(',')+1)));
							int green =  Integer.parseInt(l.substring(l.indexOf(',',l.indexOf(',')+1)+1));
							Color color = null;
							rectangleCommand.setColor( Color.rgb(red,blue,green));
							state =8;
						}
						else{error("Expected Rectangle color");
							return false;
						}
						break;
						case 8:  // Rectangle filled
							if (pfill.matcher(l).matches()){
								String bool = l.substring(7);
								if (bool.equals("true")){
									rectangleCommand.setFill(true);}
								else if (bool.equals("false")){
									rectangleCommand.setFill(false);}
								state=9;
							}
							else{error("Expected Rectangle filled");
								return false;}
							break;
						case 9: // Rectangle p1
							if (pp1.matcher(l).matches()){
								int xpp1 = Integer.parseInt(l.substring(4,l.indexOf(',')));
								int ypp1 = Integer.parseInt(l.substring(l.indexOf(',')+1,l.length()-1));
								rectangleCommand .setP1(new Point(xpp1,ypp1));
								state =10;
							}
							else{error("Expected Rectangle p1");
								return false;}
							break;
						case 10: // Rectangle p2
							if (pp2.matcher(l).matches()){
								int xpp2 = Integer.parseInt(l.substring(4,l.indexOf(',')));
								int ypp2 = Integer.parseInt(l.substring(l.indexOf(',')+1,l.length()-1));
								rectangleCommand .setP2(new Point(xpp2,ypp2));
								state =11;
							}
							else{error("Expected Rectangle p2");
								return false;}
							break;
						case 11: // Rectangle end
							if (pRectangleEnd.matcher(l).matches()){
								this.paintModel.addCommand(rectangleCommand);
								rectangleCommand=null;
								state=1;
							}
							else{error("Expected End Rectangle");
								return false;}
							break;
						case 12: // Squiggle color
							if (pcolor.matcher(l).matches()){
								int red = Integer.parseInt(l.substring(6,l.indexOf(',')));
								int blue = Integer.parseInt(l.substring(l.indexOf(',')+1,l.indexOf(',',l.indexOf(',')+1)));
								int green =  Integer.parseInt(l.substring(l.indexOf(',',l.indexOf(',')+1)+1));
								Color color = null;
								squiggleCommand.setColor( Color.rgb(red,blue,green));
								state =13;
							}
							else{error("Expected Squiggle color");
								return false;
							}
							break;
						case 13: // Squiggle filled
							if (pfill.matcher(l).matches()){
								String bool = l.substring(7);
								if (bool.equals("true")){
									squiggleCommand.setFill(true);}
								else if (bool.equals("false")){
									squiggleCommand.setFill(false);}
								state=14;
							}
							else{error("Expected Squiggle filled");
								return false;}
							break;
						case 14: // Expect "points"
							if (l.equals("points")){
								state =15;
							}
							else{error("Expected Squiggle point");
								return false;}
							break;
						case 15: // Parse squiggle points
							if (ppoint.matcher(l).matches()){
								int xpoint = Integer.parseInt(l.substring(7,l.indexOf(',')));
								int ypoint = Integer.parseInt(l.substring(l.indexOf(',')+1,l.length()-1));
								squiggleCommand.add(new Point(xpoint,ypoint));
							}
							else if (l.equals("endpoints")){
								state =16;
							}
							else{error("Expected Squiggle point or end points");
								return false;}
							break;
						case 16: // End of Squiggle
							if (pSquiggleEnd.matcher(l).matches()){
								this.paintModel.addCommand(squiggleCommand);
								squiggleCommand=null;
								state=1;
							}
							else{error("Expected End Squiggle");
								return false;}
							break;
						case 17: // Polyline color
							if (pcolor.matcher(l).matches()){
								int red = Integer.parseInt(l.substring(6,l.indexOf(',')));
								int blue = Integer.parseInt(l.substring(l.indexOf(',')+1,l.indexOf(',',l.indexOf(',')+1)));
								int green =  Integer.parseInt(l.substring(l.indexOf(',',l.indexOf(',')+1)+1));
								Color color = null;
								polylineCommand.setColor( Color.rgb(red,blue,green));
								state =18;
							}
							else{error("Expected Polyline color");
								return false;
							}
							break;
						case 18: // Polyline filled
							if (pfill.matcher(l).matches()){
								String bool = l.substring(7);
								if (bool.equals("true")){
									polylineCommand.setFill(true);}
								else if (bool.equals("false")){
									polylineCommand.setFill(false);}
								state=19;
							}
							else{error("Expected Polyline filled");
								return false;}
							break;
						case 19: // Expect "points"
							if (l.equals("points")){
								state =20;
							}
							else{error("Expected Polyline point");
								return false;}
							break;
						case 20: // Parse polyline points
							if (ppoint.matcher(l).matches()){
								int xpoint = Integer.parseInt(l.substring(7,l.indexOf(',')));
								int ypoint = Integer.parseInt(l.substring(l.indexOf(',')+1,l.length()-1));
								polylineCommand.add(new Point(xpoint,ypoint));
							}
							else if (l.equals("endpoints")){
								state =21;
							}
							else{error("Expected Polyline point or end points");
								return false;}
							break;
						case 21: // End of Polyline
							if (pPolylineEnd.matcher(l).matches()){
								this.paintModel.addCommand(polylineCommand);
								polylineCommand=null;
								state=1;
							}
								else{error("Expected End Polyline");
								return false;}
							break;
						case 22: // Extra lines after valid end
							error("Extra content after End of File");

							return false;

			}}
			}
		}  catch (Exception e){
			// Silent fail

		}
		if (retVal){return true;}
		else {return false;}

	}
}
