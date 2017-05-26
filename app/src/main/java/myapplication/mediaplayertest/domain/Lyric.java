package myapplication.mediaplayertest.domain;

/**
 * Created by zhouzhou on 2017/5/26.
 */

public class Lyric {
    private String content;
    private long timePoint;
    private long sleepTime;

    public Lyric() {

    }

    @Override
    public String toString() {
        return "Lyric{" +
                "content='" + content + '\'' +
                ", timePoint=" + timePoint +
                ", sleepTime=" + sleepTime +
                '}';
    }

    public Lyric(String content, long timePoint, long sleepTime) {
        this.content = content;
        this.timePoint = timePoint;
        this.sleepTime = sleepTime;
    }

    public String getContent() {

        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(long timePoint) {
        this.timePoint = timePoint;
    }

    public long getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }
}
