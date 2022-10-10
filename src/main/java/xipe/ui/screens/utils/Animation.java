package xipe.ui.screens.utils;

public class Animation {
	
	 public static double animation(double current, double end, double factor, double start) {
	        double progress = (end - current) * factor;

	        if (progress > 0) {
	            progress = Math.max(start, progress);
	            progress = Math.min(end - current, progress);
	        } else if (progress < 0) {
	            progress = Math.min(-start, progress);
	            progress = Math.max(end - current, progress);
	        }

	        return current + progress;
	    }

}
