// IMusicPlayService.aidl
package myapplication.mediaplayertest;

// Declare any non-default types here with import statements

interface IMusicPlayService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

             void openAudio(int position);
             void start();
             void pause();
             String getAetistName();
             String getAudioPath();
             int getDuration();
             void seekTo(int position);
             void next();
             void pre();
              boolean isPlaying();
}
