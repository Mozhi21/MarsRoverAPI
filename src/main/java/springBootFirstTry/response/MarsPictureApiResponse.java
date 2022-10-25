package springBootFirstTry.response;

import java.util.ArrayList;
import java.util.List;
/**
 * container of mars photos
 * used by homeController.
 * @author 123456
 *
 */
public class MarsPictureApiResponse {
	List<MarsPhoto> photos = new ArrayList<>();

	public List<MarsPhoto> getPhotos() {
		return photos;
	}

	public void setPhotos(List<MarsPhoto> photos) {
		this.photos = photos;
	}

	@Override
	public String toString() {
		return "MarsPictureApiResponse [photos=" + photos + "]";
	}
}
