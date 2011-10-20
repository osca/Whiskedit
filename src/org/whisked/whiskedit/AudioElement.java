package org.whisked.whiskedit;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.AbstractID3v2;
import org.farng.mp3.id3.ID3v1;

public class AudioElement {
	
	//General Variabl
	private static String root;
	
	//Mp3 Info
	private String path;
	private String artist;
	private String title;
	private String genre;
	private String year;
	private String comment;
	private Date date;
	
	/*public static void main(String[] args) {
		AudioElement element = new AudioElement("/Users/dennis/Desktop/Milton Channels - The Bat (Original Mix).mp3");
	}*/
	
	public AudioElement(String ppath) {
		path = ppath;
		try {
			File file = new File(path);
			MP3File mp3 = new MP3File(file);
			if(mp3.hasID3v2Tag()) {
				AbstractID3v2 tag = mp3.getID3v2Tag();
				artist = tag.getLeadArtist();
				title = tag.getSongTitle();
				genre = tag.getSongGenre();
				year = tag.getYearReleased();
				comment = tag.getSongComment();
				if(artist == "" && title == "" && genre == "" && year == "" && comment == "") {
					artist = mp3.getMp3file().getName();
					title = genre = year = comment = "";
				}
				System.out.println("ID3v2: ");
			}
			else if(mp3.hasID3v1Tag()) {
				ID3v1 tag = mp3.getID3v1Tag();
				artist = tag.getArtist();
				title = tag.getTitle();
				genre = Byte.toString(tag.getGenre());
				year = tag.getYear();
				comment = tag.getComment();
				if(artist == "" && title == "" && genre == "" && year == "" && comment == "") {
					artist = mp3.getMp3file().getName();
					title = genre = year = comment = "";
				}
				System.out.println("ID3v1: ");
			}
			else {
				artist = mp3.getMp3file().getName();
				title = genre = year = comment = "";
				System.out.println("No ID3: ");
			}
			Date d = new Date(file.lastModified());
			System.out.println(artist + " - " + title + " " + genre + " " + year + " " + comment + " " + d.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
