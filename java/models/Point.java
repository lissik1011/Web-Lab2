package models;

import java.io.Serializable;
import java.math.BigDecimal;

public class Point implements Serializable{

    private BigDecimal x;
    private int y;
    private float r;
    private boolean result;
    private String date;
    private long nano;

    public Point(BigDecimal x, int y, float r, boolean result, String date, long nano) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
        this.date = date;
        this.nano = nano;
    }

    public Point(BigDecimal x, int y, float r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public Point(String x, String y, String r) {
        this.x = new BigDecimal(x);
        this.y = Integer.parseInt(y);
        this.r = Float.parseFloat(r);
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setR(float r) {
        this.r = r;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNano(long nano) {
        this.nano = nano;
    }

    public BigDecimal getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float getR() {
        return r;
    }

    public boolean getResult() {
        return result;
    }

    public String getDate() {
        return date;
    }

    public long getNano() {
        return nano;
    }

    @Override
    public String toString() {
        return String.format(
            "Point:\nX=%s,\nY=%s,\nR=%s,\nresult=%s,\ndate=%s,\ndiration=%s\n",
            this.x,
            this.y,
            this.r,
            result,
            date,
            nano
        );
    }

    public String toHtml() {
        String content = """
            <tr>
                <td>%s</td>
                <td>%s</td>
                <td>%s</td>
                <td>%s</td>
                <td>%s</td>
                <td>%sμs</td>
            </tr>
            """.formatted(this.r, this.x, this.y, result, date, nano);
        return content;
    }
}
