package cr.ac.baselaboratorio

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.documentfile.provider.DocumentFile
import org.w3c.dom.Document
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var buttonPrevious: Button
    private lateinit var buttonPlay: Button
    private lateinit var buttonStop: Button
    private lateinit var buttonNext: Button
    private lateinit var songName: TextView

    private var track = 2
    var mediaPlayer = MediaPlayer()
    private lateinit var arrayTracks: Array<DocumentFile>

    companion object {
        var OPEN_DIRECTORY_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonPlay = findViewById(R.id.buttonPlay)
        buttonNext = findViewById(R.id.buttonNext)
        buttonPrevious = findViewById(R.id.buttonPrev)
        buttonStop = findViewById(R.id.buttonStop)
        songName = findViewById(R.id.songName)


        var intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        startActivityForResult(intent, OPEN_DIRECTORY_REQUEST_CODE)
        setOnClickListeners(this)

    }

    private fun setOnClickListeners(context: Context) {
        buttonPlay.setOnClickListener {
            mediaPlayer = MediaPlayer.create(context, arrayTracks[track].uri)
            mediaPlayer.start()
            Toast.makeText(context, "Reproduciendo...", Toast.LENGTH_SHORT).show()
            songName.text = arrayTracks[track].name.toString()
        }

        buttonStop.setOnClickListener {
            mediaPlayer.stop()
            mediaPlayer = MediaPlayer.create(context, arrayTracks[track].uri)
            Toast.makeText(context, "Parando...", Toast.LENGTH_SHORT).show()
            songName.text = arrayTracks[track].name.toString()

        }
        buttonPrevious.setOnClickListener {
            if(track == 1){
                mediaPlayer.stop()
                mediaPlayer = MediaPlayer.create(context, arrayTracks[track].uri)
                mediaPlayer.start()
                Toast.makeText(context, "Reproduciendo...", Toast.LENGTH_SHORT).show()
                songName.text = arrayTracks[track].name.toString()

            }
            else if(track > 1){
                track -= 1
                mediaPlayer.stop()
                mediaPlayer = MediaPlayer.create(context, arrayTracks[track].uri)
                mediaPlayer.start()
                Toast.makeText(context, "Reproduciendo...", Toast.LENGTH_SHORT).show()
                songName.text = arrayTracks[track].name.toString()

            }
        }
        buttonNext.setOnClickListener {
            track += 1
            if(track > arrayTracks.size-1){
                track = 1
                mediaPlayer.stop()
                mediaPlayer = MediaPlayer.create(context, arrayTracks[track].uri)
                mediaPlayer.start()
                Toast.makeText(context, "Reproduciendo...", Toast.LENGTH_SHORT).show()
                songName.text = arrayTracks[track].name.toString()

            }
            else if(track <= arrayTracks.size-1){
                mediaPlayer.stop()
                mediaPlayer = MediaPlayer.create(context, arrayTracks[track].uri)
                mediaPlayer.start()
                Toast.makeText(context, "Reproduciendo...", Toast.LENGTH_SHORT).show()
                songName.text = arrayTracks[track].name.toString()

            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OPEN_DIRECTORY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                var directoryURI = data?.data ?: return
                //Log.e("Directorio", directoryURI.toString())
                val rootTree = DocumentFile.fromTreeUri(this, directoryURI)
                arrayTracks = rootTree!!.listFiles()

            }
        }
    }
}