package AssistClass;

import java.io.IOException;
import java.util.HashMap;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Vibrator;
import android.util.Log;
/**
 * 바야바 엔진 기반 SoundPool, MediaPlayer 및 Viberator 처리 통합 모듈
 * Created by KIMDONGWOO on 2016-07-18.
 */
public class AudioVibeEffect {
	
	private Context mainContext;
	
	private HashMap<String, Integer> sndBuffer;
	private HashMap<String, MediaPlayer> mediaPlayer; 
	
	public AudioManager am;
	private SoundPool sndPool;
	private Vibrator Vibrator;
	
	public AudioVibeEffect( Context mainContext, int SoundPoolSize ) {
		sndBuffer = new HashMap<String, Integer>();
		mediaPlayer = new HashMap<String, MediaPlayer>();
		this.mainContext = mainContext;
		
		sndPool = new SoundPool(SoundPoolSize, AudioManager.STREAM_MUSIC, 0);	//사운드 풀 
		am =(AudioManager)mainContext.getSystemService( Context.AUDIO_SERVICE );
		Vibrator =(Vibrator) mainContext.getSystemService(Context.VIBRATOR_SERVICE);
	}
	
	/**
	 * 모든 버퍼 초기화
	 */
	public void reset() {
		sndBuffer.clear();
		mediaPlayer.clear();
	}
	
	/**
	 * 사운드 버퍼에 Asset 폴더에 존재하는 오디오 파일 등록
	 * @param name	키값으로 사용될 이름
	 * @param sndNamePath	실제 Asset 폴더 이하 파일 패스
	 */
	public void setSndBuffer( String name, String sndNamePath ) {
		sndBuffer.put( name, sndPool.load( getSoundPoolDescruptor( sndNamePath ), 0 ) );
	}
	
	/**
	 * Asset 폴더로부터 파일 읽어오는 메소드
	 * @param fileName
	 * @return AssetFileDescriptor
	 */
	private AssetFileDescriptor getSoundPoolDescruptor( String fileName) {
		
		AssetManager am = mainContext.getAssets(); 
		AssetFileDescriptor descriptor = null;
		
		try {
			descriptor = am.openFd( fileName );
		} catch (IOException e) {
			Log.i("Moie", "Sound Descriptor load error from asset folder!!" );
		}
		
		return descriptor;
	}
	
	/****사운드버퍼 재생******/
	
	/**
	 * SoundBuffer에 저장된 효과음 재생
	 * @param name 등록된 효과음 키값
	 */
	public void playSndBuffer( String name, float volume ) {
		if( am.getRingerMode() == 2 ) {
			sndPool.play( sndBuffer.get( name ), volume, volume, 0, 0, 1f );
		}
		
	}
	
	/**
	 * 진동 효과
	 * @param valMsec 기본 설정된 값에 입력된 값만큼 추가 시간동안 진동, 0이면 설정된 만큼 진동
	 */
	public void playVibrator( int valMsec ) {
		if( am.getRingerMode() >= 1 ) {
			Vibrator.vibrate( valMsec );
		}
	}
	
	/**
	 * 효과음과 진동을 같이 재생
	 * @param name	효과음 키값
	 * @param valMsec 진동 추가시간
	 */
	public void playSndBufferWithVibrator( String name , float volume,  int valMsec ) {
		playSndBuffer(name, volume);
		playVibrator( valMsec );
		
	}
	
	/****** 미디어 관리 파트 ****************************************/
	
	/**
	 * 리소스로 존재하는 Media 리소스를 MediaPlayer 객체로 등록
	 * @param name	키값으로 사용될 이름
	 * @param mediaResourceId MediaPlayer 로 등록될 리소스 ID
	 */
	public void addMediaPlayer( String nameKey, int mediaResourceId ) {
		mediaPlayer.put( nameKey, MediaPlayer.create(mainContext, mediaResourceId) );
		try {
			mediaPlayer.get( nameKey ).prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	public void addMediaPlayerByFile( String nameKey, String filePath ) {
//		mediaPlayer.put( nameKey, MediaPlayer.create(mainContext, ) );
//		try {
//			mediaPlayer.get( nameKey ).prepare();
//		} catch (IllegalStateException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
	/**
	 * MediaPlayer 에 등록된 Media 객체를 제거
	 * @param nameKey
	 */
	public void removeMediaPlayer( String nameKey ) {
		mediaPlayer.remove( nameKey );
	}
	
	/**
	 * 미디어 재생
	 * @param nameKey 재생 미디어 이름
	 * @param seekToMsec 재생 시점, 재생하고자 하는 위치를 밀리세컨드로 입력
	 * @param looping 반복 여부
	 * @param volume 볼륨, 0f~1f
	 */
	public void playMedia( String nameKey, int seekToMsec, boolean looping, float volume  ) {
		
		if( am.getRingerMode() == 2 ) {
			mediaPlayer.get( nameKey ).setVolume( volume, volume);
			mediaPlayer.get( nameKey ).setLooping(looping);
			mediaPlayer.get( nameKey ).seekTo(seekToMsec);
			mediaPlayer.get( nameKey ).start();
		}
		
	}
	
	public MediaPlayer getMediaPlayer( String nameKey ) {
		return mediaPlayer.get( nameKey );
	}
	
	/**
	 * 미디어 재생 중지
	 * @param nameKey
	 */
	public void stopMedia( String nameKey ) {
		mediaPlayer.get( nameKey ).stop();
		
	}
	
	/**
	 * 미디어 재생 일시 정지
	 * @param nameKey
	 */
	public void pauseMedia( String nameKey ) {
		mediaPlayer.get( nameKey ).pause();
		
	}
	
	float volumeForFade = 0.8f;	//페이드 아웃용 볼륨
	public  float startVolume1 = 1;
	public  String nameKey1;
	public float fadeSpeed1;
	
	/**
	 * 미디어 페이드 아웃 - 0.1초 단위로 fadeSpeed 값 만큼 볼륨이 줄어든다.
	 * @param nameKey	페이드 아웃 처리할 미디어 이름
	 * @param startVolume	페이드아웃시 시작될 볼륨
	 * @param fadeSpeed	페이드 아웃 속도 0f ~ 1f 사이
	 */
	public void fadeOutMusic( String nameKey , float startVolume, float fadeSpeed ) {
		startVolume1 = startVolume;
		nameKey1 = nameKey;
		fadeSpeed1 = fadeSpeed;
		
		if( isPlayingMedia( nameKey ) ) {	//타이틀 음악 연주중이면 페이드아웃
			
			new Thread( new Runnable() {
				
				@Override
				public void run() {
					for( volumeForFade = startVolume1; volumeForFade >= 0; volumeForFade -= fadeSpeed1 ) {
						setVolumeMedia( nameKey1, volumeForFade -= fadeSpeed1 );
						
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						if( volumeForFade <= 0.1f ) {
							pauseMedia( nameKey1 );
						}
					}
				}
				
			}).start();
			
		}
		
	}
	
	/**
	 * 미디어 재생상태 리턴
	 * @param nameKey
	 * @return
	 */
	public boolean isPlayingMedia( String nameKey ) {
		return mediaPlayer.get( nameKey ).isPlaying();
	}
	
	/**
	 * 미디어 볼륨 조절
	 * @param nameKey
	 * @param volume
	 */
	public void setVolumeMedia( String nameKey, float volume ) {
		mediaPlayer.get( nameKey ).setVolume( volume, volume);
	}
	
}
