import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private final static String INPUT = "00111101111101000";
    // private final static int DISK_LENGTH = 272;
    private final static int DISK_LENGTH = 35651584;

    public static void main(String[] args) {
	List<Boolean> diskContent = INPUT.chars().mapToObj(ch -> (char) ch).map(ch -> ch == '1')
		.collect(Collectors.toList());
	while (diskContent.size() < DISK_LENGTH) {
	    diskContent.add(false);
	    for (int i = diskContent.size() - 2; i >= 0; i--) {
		diskContent.add(!diskContent.get(i));
	    }
	}
	diskContent = diskContent.subList(0, DISK_LENGTH);
	while (diskContent.size() % 2 == 0) {
	    List<Boolean> result = new ArrayList<>();
	    for (int i = 0; i < diskContent.size(); i = i + 2) {
		result.add(diskContent.get(i) == diskContent.get(i + 1));
	    }
	    diskContent = result;
	}
	System.out.println(diskContent.stream().map(b -> b ? "1" : "0").collect(Collectors.joining("")));
    }

}
