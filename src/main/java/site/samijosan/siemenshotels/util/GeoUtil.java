package site.samijosan.siemenshotels.util;

public class GeoUtil {
    private static final double EARTH_RADIUS = 6371000;

    public static double[] toCartesian(double latitude, double longitude) {
        double latRad = Math.toRadians(latitude);
        double lonRad = Math.toRadians(longitude);

        double x = EARTH_RADIUS * Math.cos(latRad) * Math.cos(lonRad);
        double y = EARTH_RADIUS * Math.cos(latRad) * Math.sin(lonRad);
        double z = EARTH_RADIUS * Math.sin(latRad);

        return new double[]{x, y, z};
    }

    public static double calculateDistance(double[] point1, double[] point2) {
        double dx = point1[0] - point2[0];
        double dy = point1[1] - point2[1];
        double dz = point1[2] - point2[2];

        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}