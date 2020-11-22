package ruleta.negocio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;

/**
 * @author Juan Sebastian Matta Morales
 */
public class GestionRuletaNegocio implements Serializable {

	private static final long serialVersionUID = -8269977473603267089L;
	private Logger log = Logger.getLogger(GestionRuletaNegocio.class);
	private String rut = "/home/archive.txt";

	public JsonObjectBuilder rouletteOpeningDeal (){	
		File archive = new File(rut);
		FileReader fr = null;
		BufferedReader br = null; 
		FileWriter fw= null;
		PrintWriter pw = null;
		String content = "";
		String[] lines ;
		int count = 0;
		try {
			if(archive.exists()) {
				String line;
				fr = new FileReader(archive);
				br = new BufferedReader(fr);
				while((line=br.readLine())!=null) {
					if(line.contains("Id")&&count==0) {
						count=1;
					}
					count = count + 1;
					content = content + "" + line+ ";";
				}	
				fw = new FileWriter(archive);
				pw = new PrintWriter(fw);
				lines = content.split(";");
				for (int i = 0; i < lines.length; i++) {
					pw.println(lines[i]);
				}
				if(count==0) {
					count=1;
					pw.println("Id:1");
				}
				else {
					pw.println("Id:"+count);
				}
				fr.close();
				fw.close();
				br.close();
				pw.close();
			} else {
				count=1;
				File rut = new File("/home");
				if (!rut.exists()) {
					rut.mkdirs();
				}
				archive.createNewFile();
				fw = new FileWriter(archive);
				pw = new PrintWriter(fw);
				pw.println("Id:"+count);
				fw.close();
				pw.close();
			}			
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
		responseBuilder.add("Id roulette", count+"");

		return responseBuilder;
	}

	public JsonObjectBuilder rouletteOpening (String parameter){	
		String idRoulette = "";		
		File archive = new File(rut);
		FileReader fr = null;
		BufferedReader br = null; 
		FileWriter fw= null;
		PrintWriter pw = null;
		String content = "";
		String[] lines ;
		JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
		try {
			JSONObject JSon= new JSONObject(parameter);
			idRoulette=JSon.getString("id");
			if(archive.exists()) {
				String line;
				fr = new FileReader(archive);
				br = new BufferedReader(fr);
				boolean idFound = false;
				while((line=br.readLine())!=null) {								
					if(line.equals("Id:"+idRoulette)) {
						content = content + "" + line+":opened;";						
						responseBuilder.add("response", "Successful");
						idFound = true;
					}
					else {
						content = content + "" + line+ ";";
					}					
				}	
				fw = new FileWriter(archive);
				pw = new PrintWriter(fw);
				lines = content.split(";");
				for (int i = 0; i < lines.length; i++) {
					pw.println(lines[i]);
				}
				if(idFound==false) {
					responseBuilder.add("response", "Operation rejected");
				}
				fr.close();
				fw.close();
				br.close();
				pw.close();
			} else {
				responseBuilder.add("response", "Operation rejected");
			}			
		} catch (Exception e2) {
			e2.printStackTrace();
			responseBuilder.add("response", "Operation rejected");
		}
		
		return responseBuilder;
	}

	public JsonObjectBuilder betPlay (String user,String parameter){	
		String idRoulette = "";
		String bet = "";
		double betCash = 0.0;
		File archive = new File(rut);
		FileReader fr = null;
		BufferedReader br = null; 
		FileWriter fw= null;
		PrintWriter pw = null;
		String content = "";
		String[] lines ;
		JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
		try {
			JSONObject JSon= new JSONObject(parameter);
			idRoulette=JSon.getString("id");
			bet=JSon.getString("apuesta");
			betCash=JSon.getDouble("valorApuesta");			
			boolean validation = false;
			if(betValidation(bet)) {
				validation = true;
			}
			else if(cashValidation(betCash)) {
				validation = true;						
			}	
			if(validation) {
				if(archive.exists()) {
					String line;
					fr = new FileReader(archive);
					br = new BufferedReader(fr);
					boolean idFound = false;
					while((line=br.readLine())!=null) {								
						if(line.contains("Id:"+idRoulette)) {
							content = content + "" + line+" @usuer:"+user+"-bet:"+bet+"-cash:"+betCash+";";						
							responseBuilder.add("response:", "Successful bet");
							idFound = true;
						}
						else {
							content = content + "" + line+ ";";
						}					
					}	
					fw = new FileWriter(archive);
					pw = new PrintWriter(fw);
					lines = content.split(";");
					for (int i = 0; i < lines.length; i++) {
						pw.println(lines[i]);
					}
					if(idFound==false) {
						responseBuilder.add("response", "Id roulleta not found");
					}
					fr.close();
					fw.close();
					br.close();
					pw.close();
				} else {
					responseBuilder.add("response", "Operation rejected");
				}	
			}
			else {
				responseBuilder.add("response", "Wrong bet");
			}		
		} catch (Exception e2) {
			e2.printStackTrace();
			responseBuilder.add("response:", "Operation rejected");
		}
		
		return responseBuilder;
	}

	public JsonObjectBuilder betClosing (String parameter){	
		String idRoulette = "";
		File archive = new File(rut);
		FileReader fr = null;
		BufferedReader br = null; 
		FileWriter fw= null;
		PrintWriter pw = null;
		String content = "";
		String[] lines ;
		double gain = 0.0;
		JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
		String response = "";
		try {
			JSONObject JSon= new JSONObject(parameter);
			idRoulette=JSon.getString("id");				
			int number = (int) (Math.random() * (37 - 0)) + 0;
			String color = "";
			color = colorValidation(number);		
			if(archive.exists()) {
				String line;
				fr = new FileReader(archive);
				br = new BufferedReader(fr);
				boolean idFound = false;
				while((line=br.readLine())!=null) {	
					if(line.contains("Id:"+idRoulette)) {
						line=line.replaceAll("opened", "closed");
						String[] subLines ;
						subLines = line.split("@");
						response = "Number:"+number+" Color:"+color+ ". ";
						for (int j = 0; j < subLines.length; j++) {
							if(j>0) {							
								String[] bets ;
								bets = subLines[j].split("-");
								String[] results ;
								results = bets[1].split(":");					
								String[] cash ;
								cash = bets[2].split(":");
								if(isNumeric(results[1])) {
									if(Integer.parseInt(results[1].trim())==number) {		
										gain = Double.parseDouble(cash[1]) * 5;
										response = response +" / "+ subLines[j] + " win: "+gain;
									}
								}
								else {
									if(results[1].trim().equals(color)) {
										gain = Double.parseDouble(cash[1]) * 1.8;
										response = response +" / "+ subLines[j] + " win: "+gain;
									}
									else {
										response = response +" / "+ subLines[j] + "the house wins ";
									}
								}
							}
						}
						content = content + "" + line+";";						

						idFound = true;
					}
					else {
						content = content + "" + line+ ";";
					}					
				}
				if(response.equals("")) {
					responseBuilder.add("response:", "Roolette without bets");
				}
				responseBuilder.add("response:", response);
				fw = new FileWriter(archive);
				pw = new PrintWriter(fw);
				lines = content.split(";");
				for (int i = 0; i < lines.length; i++) {
					pw.println(lines[i]);
				}
				if(idFound==false) {
					responseBuilder.add("response", "Id roulleta not found");
				}
				fr.close();
				fw.close();
				br.close();
				pw.close();
			} else {
				responseBuilder.add("response", "Operation rejected");
			}	
		} catch (Exception e2) {
			e2.printStackTrace();
			responseBuilder.add("response:", "Operation rejected");
		}
		
		return responseBuilder;
	}

	public JsonObjectBuilder rouletteStatus (){	
		File archive = new File(rut);
		FileReader fr = null;
		BufferedReader br = null; 
		String response = "";
		try {
			if(archive.exists()) {
				String line;
				fr = new FileReader(archive);
				br = new BufferedReader(fr);
				while((line=br.readLine())!=null) {
					String[] subLines ;
					subLines = line.split("@");					
					String[] id ;
					id = subLines[0].split(":");				
					response = response + " / ID:"+id[1]+" - state:"+id[2];
				}				
				fr.close();
				br.close();
			} 
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
		responseBuilder.add("response", response);
		
		return responseBuilder;
	}

	private static boolean isNumeric(String string){
		try {
			Integer.parseInt(string);
			
			return true;
		} catch (NumberFormatException nfe){
			
			return false;
		}
	}

	public String colorValidation(int number) {
		String response = "";
		if (number%2==0)
			response="rojo";
		else
			response="negro";
		return response;
	}

	public boolean betValidation(String bet) {
		boolean response = false;
		int betNumber = 0;
		if(bet.equals("rojo") || bet.equals("negro") ) {
			response = true;
		}
		else if(isNumeric(bet)) {
			betNumber=Integer.parseInt(bet);
			if(betNumber>=0&&betNumber<=36) {
				response = false;
			}
			else {
				response = false;
			}
		}
		
		return response;
	}

	public boolean cashValidation(double cash) {
		boolean response = false;
		if(cash>0.0 && cash<10000.1) {
			response = true;
		}
		else {
			response = false;
		}
		
		return response;
	}
}