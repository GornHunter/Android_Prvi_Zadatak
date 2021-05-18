package com.example.mp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SecondActivity extends AppCompatActivity implements Runnable {

    //atributi
    private MediaPlayer mediaPlayer;
    FloatingActionButton fabPlayPause;
    FloatingActionButton fabRewind;
    FloatingActionButton fabForward;
    SeekBar seekBar;
    TextView tvProgress;
    ImageView image;
    TextView song;

    //indikator da je pocela reprodukcija zbog niti koja azurira stanje seekBar-a
    boolean started;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //intenti
        Intent intent = getIntent();
        String m = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        //inicijalizacija atributa i indikatora
        started = false;

        tvProgress = (TextView) findViewById(R.id.timeProgress);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        image = (ImageView) findViewById(R.id.imageView2);
        song = (TextView) findViewById(R.id.songName);

        image.setImageResource(Integer.parseInt(m.split("!")[0]));
        song.setText(m.split("!")[1]);

        //u ovom primeru pusta se uvek ista pesma
        mediaPlayer = MediaPlayer.create(this, Integer.parseInt(m.split("!")[2]));

        fabPlayPause = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fabRewind = (FloatingActionButton) findViewById(R.id.floatingActionButtonRew);
        fabForward = (FloatingActionButton) findViewById(R.id.floatingActionButtonFF);

        //postavljanje OnCompletionListener-a na objektu mediaPlayer
        //on ce nam omoguciti da detektujemo da se doslo do kraja reprodukcije pesme
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //Toast poruka da je pesma zavrsena
                //Obratite paznju na MainActivity.this, sto je neophodno jer bi samo this upucivalo
                //na OnCompletionListener objekat koji trenutno kreiramo kao objekat anonimne klase
                //Posto sve ovo radimo unutar klase MainActivity, sa MainActivity.this mozemo dobiti
                //referencu na objekat klase MainActivity

                //Toast.makeText(SecondActivity.this, R.string.songComplete, Toast.LENGTH_SHORT).show();

                //reprodukcija je zavrsena, omoguci da se ponovo moze pustiti iz pocetka
                fabPlayPause.setImageDrawable(ContextCompat.getDrawable(SecondActivity.this, android.R.drawable.ic_media_play));
                started = false;

                //azuriraj komponente seekBar i textView koji pokazuje vreme
                seekBar.setProgress(0);
                tvProgress.setText("00:00");

                //vrati se na prvu aktivnost
                finish();
            }
        });

        fabPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pusti/pauziraj pesmu i promeni izgled dugmeta
                if (!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                    fabPlayPause.setImageDrawable(ContextCompat.getDrawable(SecondActivity.this, android.R.drawable.ic_media_pause));
                }
                else{
                    mediaPlayer.pause();
                    fabPlayPause.setImageDrawable(ContextCompat.getDrawable(SecondActivity.this, android.R.drawable.ic_media_play));
                }
                //detektuj prvo pokretanje kako bi kreirao novu nit u kojoj ces osvezavati
                //seekBar
                if (!started){
                    started = true;
                    //dodali smo da MainActivity implementira interfejs runnable, tako da
                    //nju mozemo koristiti prilikom startovanja nove niti (imamo run metodu)
                    new Thread(SecondActivity.this).start();
                }
            }
        });

        fabRewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = seekBar.getProgress();
                int duration_s = currentPosition / 1000;
                int sec = duration_s % 60;
                int min = duration_s / 60;

                if((min*60 + sec) >= 10) {
                    int newCur= (min*60 + sec) - 10;
                    mediaPlayer.seekTo(newCur * 1000);
                    String currPos = timeStringFromMiliSec(newCur * 1000);
                    String complete = currPos + "/" + timeStringFromMiliSec(mediaPlayer.getDuration());
                    tvProgress.setText(complete);
                }
                else
                    Toast.makeText(SecondActivity.this, R.string.noRewind, Toast.LENGTH_SHORT).show();
            }
        });

        fabForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = seekBar.getProgress();
                int duration_s = currentPosition / 1000;
                int sec = duration_s % 60;
                int min = duration_s / 60;

                //if((min*60 + sec) + 10 <= mediaPlayer.getDuration()){
                int newCur= (min*60 + sec) + 10;
                mediaPlayer.seekTo(newCur * 1000);
                String currPos = timeStringFromMiliSec(newCur * 1000);
                String complete = currPos + "/" + timeStringFromMiliSec(mediaPlayer.getDuration());
                tvProgress.setText(complete);
                //}
            }
        });

        //postavi da seekBar ima maksimalnu vrednost jednaku trajanju pesme (u ms)
        seekBar.setMax(mediaPlayer.getDuration());

        //callback metode za pracenje statusa seekBar-a
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //za sad ce stajati
                //azuriraj text view koji prikazuje trenutno protekle sekunde i ukupno trajanje
                String currPos = timeStringFromMiliSec(seekBar.getProgress());
                String complete = currPos + "/" + timeStringFromMiliSec(mediaPlayer.getDuration());
                tvProgress.setText(complete);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //ako je pesma pustena proveri gde je zavrsio pokazivac na seekBar-u i
                //pomeri pesmu da se reprodukuje od tog dela
                if (mediaPlayer.isPlaying()){
                    int currentPosition = seekBar.getProgress();
                    mediaPlayer.seekTo(currentPosition);
                    //azuriraj text view koji prikazuje trenutno protekle sekunde i ukupno trajanje
                    String currPos = timeStringFromMiliSec(currentPosition);
                    String complete = currPos + "/" + timeStringFromMiliSec(mediaPlayer.getDuration());
                    tvProgress.setText(complete);
                }
            }
        });
    }

    //Konvertuje ms u string formata mm:ss
    private String timeStringFromMiliSec(int ms){
        int duration_s = ms / 1000;
        int sec = duration_s % 60;
        int min = duration_s / 60;
        String elapsed = "";
        elapsed += (min < 10) ? "0" + min : min;
        elapsed += ":";
        elapsed += (sec < 10) ? "0" + sec : sec;
        return elapsed;
    }

    //Nit koja treba da azurira stanje seekBar-a i ispisa vremena
    @Override
    public void run() {
        //proveri proteklo vreme i ukupno vreme pesme u ms
        int currentPosition = mediaPlayer.getCurrentPosition();
        int total = mediaPlayer.getDuration();

        //sve dok ne dodjes do kraja
        while(mediaPlayer != null && currentPosition <= total){
            try {
                //sacekaj 1s
                Thread.sleep(1000);
                //proveri ponovo proteklo vreme
                currentPosition=mediaPlayer.getCurrentPosition();
            }
            //catch(InterruptedException e){
            //    return;
            //}
            catch(Exception e){
                return;
            }
            //ako je reprodukcija zavrsena, izadji
            if (!started)
                break;
            //u suprotnom, pomeri seekBar
            seekBar.setProgress(currentPosition);
            int finalCurrentPosition = currentPosition;
            //napravi string koji treba da se prikaze
            String currPos = timeStringFromMiliSec(currentPosition);
            String complete = currPos + "/" + timeStringFromMiliSec(total);
            //Vazno: nije dozvoljeno azurirati komponente iz niti u kojoj nisu kreirane, zato se
            //koristi metoda runOnUIThread da bi se kod koji stoji u run metodi izvrsio kada ponovo
            //bude aktivna UI (User Interface) nit
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvProgress.setText(complete);
                }
            });
        }
    }
}