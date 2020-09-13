import movieObject.dataObj;
import movieObject.witObj;
import org.json.JSONObject;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.awt.Font;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class movieDebate {

    //SOUNDAPI        //SOUNDAPI        //SOUNDAPI    //SOUNDAPI        //SOUNDAPI        //SOUNDAPI

    static final long RECORD_TIME = 7000;  // 7 seconds
    File wavFile = new File("C:/DATA/Projects/movieDebate/src/audioFIle/RecordAudio.wav");
    //C:\DATA\Projects\movieDebate\src
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    TargetDataLine line;

    AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                channels, signed, bigEndian);
        return format;
    }

    void start() {
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

             if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Unsupported Line");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
//            System.out.println("Capturing...");

            AudioInputStream ais = new AudioInputStream(line);

//            System.out.println("Recording...");

            AudioSystem.write(ais, fileType, wavFile);

        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    void finish() {
        line.stop();
        line.close();
//        System.out.println("Finished");
    }


    public static void main(String[] args) throws Exception{


        JFrame primaryFrame= new JFrame("Movie-Assistant");
        JLabel l1,l2;

        l1=new JLabel("👨 ");
        l1.setBounds(60,30,200,40);
        l2=new JLabel(("💻 "));
        l2.setBounds(60,80,200,40);

        JTextField F_user = new JTextField();
        F_user.setBounds(110, 35 ,900, 30);
        JTextField F_bot = new JTextField();
        F_bot.setBounds(110, 85 ,900, 30);

        JButton but=new JButton("🎤");
        but.setBounds(500,130,70,50);

        l1.setFont(new Font("Dialog", Font.PLAIN, 35));
        l2.setFont(new Font("Dialog", Font.PLAIN, 35));
        but.setFont(new Font("Dialog", Font.PLAIN, 35));


        F_user.setFont(new Font("Futura ", Font.PLAIN, 14));
        F_bot.setFont(new Font("Futura ", Font.PLAIN, 14));
        F_user.setEditable(false);
        F_bot.setEditable(false);

        but.addActionListener(new ActionListener() {  //check action listener
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] resp = new String[0];
                try {
                    resp = onCall();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                F_user.setText(resp[0]);
                F_bot.setText(resp[1]);
            }
        });



        primaryFrame.add(but);
        primaryFrame.add(F_user);
        primaryFrame.add(F_bot);
        primaryFrame.add(l1);
        primaryFrame.add(l2);



        primaryFrame.setSize(1100,260);
        primaryFrame.setLayout(null);
        primaryFrame.setVisible(true);
        primaryFrame.setLocationRelativeTo(null);
        primaryFrame.setResizable(true);
    }


    public static String[] onCall() throws Exception
    {
        final movieDebate recorder = new movieDebate();

        String[] resp = new String[2];

        // creates a new thread that waits for a specified
        // of time before stopping
        Thread stopper = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(RECORD_TIME);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                recorder.finish();
            }
        });

        stopper.start();

        // start recording
        recorder.start();


        //APIkeys    //APIkeys    //APIkeys        //APIkeys    //APIkeys    //APIkeys        //APIkeys    //APIkeys    //APIkeys

        String APIkey;
        String OMBbAPIkey= new String();
        String witAPIkey=new String();

        File file1 = new File("C:/DATA/Projects/movieDebate/src/APIkeys/OMDbAPI.txt");
        BufferedReader reader1 = new BufferedReader(new FileReader(file1));
        while ((APIkey = reader1.readLine()) != null) {
            OMBbAPIkey=APIkey;
        }


        File file2 = new File("C:/DATA/Projects/movieDebate/src/APIkeys/witAPI.txt");
        BufferedReader reader2 = new BufferedReader(new FileReader(file2));
        while ((APIkey = reader2.readLine()) != null)
            witAPIkey=APIkey;

        //    WIT.AI     //    WIT.AI     //    WIT.AI        //    WIT.AI     //    WIT.AI     //    WIT.AI

        String url = "https://api.wit.ai/speech";
        String key = witAPIkey;

        String param1 = "20170307";
        String param2 = "command";
        String charset = "UTF-8";

        String query = String.format("v=%s",
                URLEncoder.encode(param1, charset));


        URLConnection connection = new URL(url + "?" + query).openConnection();
        connection.setRequestProperty ("Authorization","Bearer " + key);
        connection.setRequestProperty("Content-Type", "audio/wav");
        connection.setDoOutput(true);
        OutputStream outputStream = connection.getOutputStream();
        FileChannel fileChannel = new FileInputStream("C:/DATA/Projects/movieDebate/src/audioFIle/RecordAudio.wav").getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        while((fileChannel.read(byteBuffer)) != -1) {
            byteBuffer.flip();
            byte[] b = new byte[byteBuffer.remaining()];
            byteBuffer.get(b);
            outputStream.write(b);
            byteBuffer.clear();
        }

        BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String text;
        String jsonText="";

        while((text = response.readLine()) != null) {
            jsonText=jsonText+text;
        }


        witObj req= new witObj();
        req.setWitText(jsonText);
        req.setWitIntent(jsonText);
        req.setWitMovieName(jsonText);
        req.setWitGenre(jsonText);

        //OMDB API        //OMDB API        //OMDB API

        BufferedReader reader;

        if(req.getWitMovieName()!=null) {
            String movieName = req.getWitMovieName();

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://www.omdbapi.com/?t=" + movieName + "&apikey="+OMBbAPIkey)).build();

            dataObj Movie = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(movieDebate::parse)
                    .join();

            //BOT        //BOT        //BOT        //BOT            //BOT        //BOT        //BOT        //BOT

            if(Movie.getDataResponse().equals("True")) {
//                System.out.println("🧑 ➡ " + req.getWitText());
                resp[0]=req.getWitText();

                if (req.getWitIntent().equals("getGenre")) {
//                    System.out.println("💻 ➡ " + Movie.getdataTitle() + "'s genre(s) are/is " + Movie.getdataGenre());
                    resp[1]= Movie.getdataTitle() + "'s genre(s) are/is " + Movie.getdataGenre();
                } else if (req.getWitIntent().equals("getRating")) {
//                    System.out.println("💻 ➡ " + Movie.getdataTitle() + " is rated " + Movie.getdataImdbRating() + " at IMDb");
                    resp[1]=Movie.getdataTitle() + " is rated " + Movie.getdataImdbRating() + " at IMDb";
                } else if (req.getWitIntent().equals("checkGenre")) {
                    String genreChecker = Movie.getdataGenre();
//                    System.out.print(genreChecker);
                    if (genreChecker.contains(req.getWitGenre())) {
//                        System.out.println("💻 ➡ Yes, " + req.getWitGenre() + "is one of the genres of " + Movie.getdataTitle());
                        resp[1]= "Yes, " + req.getWitGenre() + " is one of the genres of " + Movie.getdataTitle();
                    } else {
//                        System.out.println("💻 ➡ No, " + req.getWitGenre() + "isn't one of the genres of " + Movie.getdataTitle());
                        resp[1]= "No, " + req.getWitGenre() + " isn't one of the genres of " + Movie.getdataTitle();
                    }

                } else if (req.getWitIntent().equals("getPlot")) {
//                    System.out.println("💻 ➡ " + Movie.getdataTitle() + " Plot: " + Movie.getdataPlot());
                    resp[1]= Movie.getdataTitle() + " Plot: " + Movie.getdataPlot();
                } else {
//                    System.out.println("💻 ➡ Sorry, Unable to understand");
                    resp[1]= "Sorry, Unable to understand the request";
                }
            }
            else {
//                System.out.println("💻 ➡ Sorry, Unable to understand");
                resp[1]="Sorry, Unable to understand the request" ;
            }
        }
        else{
//            System.out.println("💻 ➡ Movie name not found");
            resp[1]= "Movie name not found";
        }

        return resp;
    }

    public static dataObj parse(String reponseBody){
        JSONObject movie= new JSONObject(reponseBody);

        dataObj movieO = new dataObj();

        movieO.setDataResponse(movie.getString("Response"));

        if(movieO.getDataResponse().equals("True")) {

            movieO.setdataTitle(movie.getString("Title"));
            movieO.setdataImdbRating(movie.getString("imdbRating"));
            movieO.setdataGenre(movie.getString("Genre"));
            movieO.setdataPlot(movie.getString("Plot"));

        }
        return movieO;
    }

}
