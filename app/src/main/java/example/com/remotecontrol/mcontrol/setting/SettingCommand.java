package example.com.remotecontrol.mcontrol.setting;

import java.util.Map;

import example.com.remotecontrol.mbus.message.AbstractMbusCommand;
import example.com.remotecontrol.mbus.message.entity.MbusMessageType;
import example.com.remotecontrol.mbus.util.MsgDataParser;
import example.com.remotecontrol.mcontrol.setting.entity.FocusRectEntity;
import example.com.remotecontrol.mcontrol.setting.entity.MuteStatusEntity;
import example.com.remotecontrol.mcontrol.setting.entity.NetworkStatusEntity;
import example.com.remotecontrol.mcontrol.setting.entity.SensorStatusEntity;
import example.com.remotecontrol.mcontrol.setting.entity.StartTestUIEntity;
import example.com.remotecontrol.mcontrol.setting.entity.StopTestUIEntity;
import example.com.remotecontrol.mcontrol.setting.entity.VideoBitratecapEntity;
import example.com.remotecontrol.mcontrol.setting.entity.VideoFrameratecapEntity;
import example.com.remotecontrol.mcontrol.setting.entity.VideoResolutionEntity;

public class SettingCommand extends AbstractMbusCommand
{
  private static final String TAG = "SettingCommand";
  private String action;
  private NetworkStatusEntity networkStatusEntity = null;
  private SensorStatusEntity sensorStatusEntity = null;
  private MuteStatusEntity mMuteStatusEntity = null;
  private FocusRectEntity mFocusRectEntity = null;
  private boolean isResponse = false;
  private VideoResolutionEntity videoResolutionEntity;
  private VideoFrameratecapEntity videoFrameratecapEntity;
  private VideoBitratecapEntity videoBitratecapEntity;
  private StartTestUIEntity startTestUIEntity;
  private StopTestUIEntity stopTestUIEntity;

  public SettingCommand()
  {
  }

  public SettingCommand(String paramString, boolean paramBoolean, NetworkStatusEntity paramNetworkStatusEntity)
  {
    this.action = paramString;
    this.isResponse = paramBoolean;
    this.networkStatusEntity = paramNetworkStatusEntity;
  }

  public SettingCommand(String paramString, boolean paramBoolean, SensorStatusEntity paramSensorStatusEntity)
  {
    this.action = paramString;
    this.isResponse = paramBoolean;
    this.sensorStatusEntity = paramSensorStatusEntity;
  }

  public SettingCommand(String paramString, boolean paramBoolean, MuteStatusEntity paramMuteStatusEntity)
  {
    this.action = paramString;
    this.isResponse = paramBoolean;
    this.mMuteStatusEntity = paramMuteStatusEntity;
  }

  public SettingCommand(String paramString, boolean paramBoolean, FocusRectEntity paramFocusRectEntity)
  {
    this.action = paramString;
    this.isResponse = paramBoolean;
    this.mFocusRectEntity = paramFocusRectEntity;
  }

  public byte[] dataToBytes()
    throws Exception
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("action").append("=").append(getAction());
    localStringBuilder.append("&");
    if ("getnetwork".equals(this.action))
      localStringBuilder.append(encodeGetNetWorkMsgData(this.isResponse, this.networkStatusEntity));
    else if ("setnetwork".equals(this.action))
      localStringBuilder.append(encodeSetNetWorkMsgData(this.isResponse, this.networkStatusEntity));
    else if ("getsensor".equals(this.action))
      localStringBuilder.append(encodeGetSensorMsgData(this.isResponse, this.sensorStatusEntity));
    else if ("setmute".equals(this.action))
      localStringBuilder.append(encodeSetMuteMsgData(this.isResponse, this.mMuteStatusEntity));
    else if ("getmute".equals(this.action))
      localStringBuilder.append(encodeGetMuteMsgData(this.isResponse, this.mMuteStatusEntity));
    else if ("getresolutioncap".equals(this.action))
      localStringBuilder.append(encodeGetResolutionMsgData(this.isResponse, this.videoResolutionEntity));
    else if ("setresolution".equals(this.action))
      localStringBuilder.append(encodeSetResolutionMsgData(this.isResponse, this.videoResolutionEntity));
    else if ("getframeratecap".equals(this.action))
      localStringBuilder.append(encodeGetFrameratecapMsgData(this.isResponse, this.videoFrameratecapEntity));
    else if ("setframerate".equals(this.action))
      localStringBuilder.append(encodeSetFrameratecapMsgData(this.isResponse, this.videoFrameratecapEntity));
    else if ("getvideobitratecap".equals(this.action))
      localStringBuilder.append(encodeGetBitratecapMsgData(this.isResponse, this.videoBitratecapEntity));
    else if ("setvideobitrate".equals(this.action))
      localStringBuilder.append(encodeSetBitratecapMsgData(this.isResponse, this.videoBitratecapEntity));
    else if ("starttestui".equals(this.action))
      localStringBuilder.append(encodeStartTestUIMsgData(this.isResponse, this.startTestUIEntity));
    else if ("stoptestui".equals(this.action))
      localStringBuilder.append(encodeStopTestUIMsgData(this.isResponse, this.stopTestUIEntity));
    else if ("startfocusrect".equals(this.action))
      localStringBuilder.append(encodeStartFocusMsgData(this.isResponse, this.mFocusRectEntity));
    else if ("stopfocusrect".equals(this.action))
      localStringBuilder.append(encodeStopFocusMsgData(this.isResponse, this.mFocusRectEntity));
    else if ("movefocusrect".equals(this.action))
      localStringBuilder.append(encodeMoveFocusMsgData(this.isResponse, this.mFocusRectEntity));
    return localStringBuilder.toString().getBytes();
  }

  public void dataFromBytes(byte[] paramArrayOfByte)
    throws Exception
  {
    if (null == paramArrayOfByte)
      return;
    String str = new String(paramArrayOfByte);
    Map localMap = MsgDataParser.parserMsgDataByTokenizer(str);
    if ((null == localMap) || (localMap.size() == 0))
      return;
    this.action = ((String)localMap.get("action"));
    if (("getnetwork".equals(this.action)) || ("setnetwork".equals(this.action)))
    {
      this.networkStatusEntity = decodeNetworkStatusMsgData(localMap);
    }
    else if ("getsensor".equals(this.action))
    {
      this.sensorStatusEntity = decodeSensorStatusMsgData(localMap);
    }
    else if ("setmute".equals(this.action))
    {
      this.mMuteStatusEntity = decodeMuteStatusMsgData(localMap);
    }
    else if (("getresolutioncap".equals(this.action)) || ("setresolution".equals(this.action)))
    {
      this.videoResolutionEntity = decodeVideoResolutionMsgData(localMap);
    }
    else if (("getframeratecap".equals(this.action)) || ("setframerate".equals(this.action)))
    {
      this.videoFrameratecapEntity = decodeVideoFrameratecapMsgData(localMap);
    }
    else if (("getvideobitratecap".equals(this.action)) || ("setvideobitrate".equals(this.action)))
    {
      this.videoBitratecapEntity = decodeVideoBitratecapMsgData(localMap);
    }
    else if ("starttestui".equals(this.action))
    {
      this.startTestUIEntity = decodeStartTestUIMsgData(localMap);
    }
    else if ("stoptestui".equals(this.action))
    {
      this.stopTestUIEntity = decodeStopTestUIMsgData(localMap);
    }
    else if ("startfocusrect".equals(this.action))
    {
      this.mFocusRectEntity = decodeStartFocusMsgData(localMap);
    }
    else if ("stopfocusrect".equals(this.action))
    {
      this.mFocusRectEntity = decodeStopFocusMsgData(localMap);
    }
    else
    {
      if (!"movefocusrect".equals(this.action))
        return;
      this.mFocusRectEntity = decodeMoveFocusMsgData(localMap);
    }
  }

  public String getCurrentMsgType()
  {
    return MbusMessageType.MSGTYPE_REMOTE_SETTING;
  }

  public int getCurrentMsgDataLen()
  {
    try
    {
      return dataToBytes().length;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }

  private StringBuilder encodeGetNetWorkMsgData(boolean paramBoolean, NetworkStatusEntity paramNetworkStatusEntity)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (null == paramNetworkStatusEntity)
      return null;
    if (paramBoolean)
    {
      localStringBuilder.append("type").append("=").append(paramNetworkStatusEntity.getType());
      localStringBuilder.append("&");
      localStringBuilder.append("result").append("=").append(paramNetworkStatusEntity.getResult());
      localStringBuilder.append("&");
      localStringBuilder.append("name").append("=").append(paramNetworkStatusEntity.getName());
      localStringBuilder.append("&");
      localStringBuilder.append("mac").append("=").append(paramNetworkStatusEntity.getMac());
      localStringBuilder.append("&");
      localStringBuilder.append("state").append("=").append(paramNetworkStatusEntity.getState());
    }
    else
    {
      localStringBuilder.append("type").append("=").append(paramNetworkStatusEntity.getType());
    }
    return localStringBuilder;
  }

  private StringBuilder encodeSetNetWorkMsgData(boolean paramBoolean, NetworkStatusEntity paramNetworkStatusEntity)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (null == paramNetworkStatusEntity)
      return null;
    localStringBuilder.append("type").append("=").append(paramNetworkStatusEntity.getType());
    localStringBuilder.append("&");
    localStringBuilder.append("name").append("=").append(paramNetworkStatusEntity.getName());
    localStringBuilder.append("&");
    localStringBuilder.append("mac").append("=").append(paramNetworkStatusEntity.getMac());
    localStringBuilder.append("&");
    localStringBuilder.append("state").append("=").append(paramNetworkStatusEntity.getState());
    if (paramBoolean)
    {
      localStringBuilder.append("&");
      localStringBuilder.append("result").append("=").append(paramNetworkStatusEntity.getResult());
    }
    return localStringBuilder;
  }

  private NetworkStatusEntity decodeNetworkStatusMsgData(Map<String, String> paramMap)
  {
    if ((null == paramMap) || (paramMap.size() == 0))
      return null;
    NetworkStatusEntity localNetworkStatusEntity = new NetworkStatusEntity();
    localNetworkStatusEntity.setType((String)paramMap.get("type"));
    localNetworkStatusEntity.setName((String)paramMap.get("name"));
    localNetworkStatusEntity.setMac((String)paramMap.get("mac"));
    localNetworkStatusEntity.setState((String)paramMap.get("state"));
    localNetworkStatusEntity.setResult((String)paramMap.get("result"));
    return localNetworkStatusEntity;
  }

  private StringBuilder encodeGetSensorMsgData(boolean paramBoolean, SensorStatusEntity paramSensorStatusEntity)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (null == paramSensorStatusEntity)
      return null;
    if (paramBoolean)
    {
      localStringBuilder.append("state").append("=").append(paramSensorStatusEntity.getState());
      localStringBuilder.append("&");
      localStringBuilder.append("sensortype").append("=").append(paramSensorStatusEntity.getSensortype());
      localStringBuilder.append("&");
      localStringBuilder.append("result").append("=").append(paramSensorStatusEntity.getResult());
    }
    return localStringBuilder;
  }

  private SensorStatusEntity decodeSensorStatusMsgData(Map<String, String> paramMap)
  {
    if ((null == paramMap) || (paramMap.size() == 0))
      return null;
    SensorStatusEntity localSensorStatusEntity = new SensorStatusEntity();
    localSensorStatusEntity.setState((String)paramMap.get("state"));
    localSensorStatusEntity.setSensortype((String)paramMap.get("sensortype"));
    localSensorStatusEntity.setResult((String)paramMap.get("result"));
    return localSensorStatusEntity;
  }

  private StringBuilder encodeSetMuteMsgData(boolean paramBoolean, MuteStatusEntity paramMuteStatusEntity)
  {
    if (null == this.mMuteStatusEntity)
      return null;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("mutestate").append("=").append(this.mMuteStatusEntity.getMute());
    localStringBuilder.append("&");
    localStringBuilder.append("type").append("=").append(this.mMuteStatusEntity.getType());
    return localStringBuilder;
  }

  private StringBuilder encodeGetMuteMsgData(boolean paramBoolean, MuteStatusEntity paramMuteStatusEntity)
  {
    if (null == this.mMuteStatusEntity)
      return null;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("mutestate").append("=").append(this.mMuteStatusEntity.getMute());
    localStringBuilder.append("&");
    localStringBuilder.append("type").append("=").append(this.mMuteStatusEntity.getType());
    if (paramBoolean)
    {
      localStringBuilder.append("&");
      localStringBuilder.append("result").append("=").append(this.mMuteStatusEntity.getResult());
    }
    return localStringBuilder;
  }

  private MuteStatusEntity decodeMuteStatusMsgData(Map<String, String> paramMap)
  {
    if ((null == paramMap) || (paramMap.size() == 0))
      return null;
    MuteStatusEntity localMuteStatusEntity = new MuteStatusEntity();
    localMuteStatusEntity.setMute((String)paramMap.get("mutestate"));
    localMuteStatusEntity.setType((String)paramMap.get("type"));
    localMuteStatusEntity.setResult((String)paramMap.get("result"));
    return localMuteStatusEntity;
  }

  public String getAction()
  {
    return this.action;
  }

  public void setAction(String paramString)
  {
    this.action = paramString;
  }

  public NetworkStatusEntity getNetworkStatusEntity()
  {
    return this.networkStatusEntity;
  }

  public void setNetworkStatusEntity(NetworkStatusEntity paramNetworkStatusEntity)
  {
    this.networkStatusEntity = paramNetworkStatusEntity;
  }

  public boolean isResponse()
  {
    return this.isResponse;
  }

  public void setResponse(boolean paramBoolean)
  {
    this.isResponse = paramBoolean;
  }

  public SensorStatusEntity getSensorStatusEntity()
  {
    return this.sensorStatusEntity;
  }

  public void setSensorStatusEntity(SensorStatusEntity paramSensorStatusEntity)
  {
    this.sensorStatusEntity = paramSensorStatusEntity;
  }

  public MuteStatusEntity getMuteStatusEntity()
  {
    return this.mMuteStatusEntity;
  }

  public void setMuteStatusEntitiy(MuteStatusEntity paramMuteStatusEntity)
  {
    this.mMuteStatusEntity = paramMuteStatusEntity;
  }

  public SettingCommand(String paramString, boolean paramBoolean, VideoResolutionEntity paramVideoResolutionEntity)
  {
    this.action = paramString;
    this.isResponse = paramBoolean;
    this.videoResolutionEntity = paramVideoResolutionEntity;
  }

  public SettingCommand(String paramString, boolean paramBoolean, VideoFrameratecapEntity paramVideoFrameratecapEntity)
  {
    this.action = paramString;
    this.isResponse = paramBoolean;
    this.videoFrameratecapEntity = paramVideoFrameratecapEntity;
  }

  public SettingCommand(String paramString, boolean paramBoolean, VideoBitratecapEntity paramVideoBitratecapEntity)
  {
    this.action = paramString;
    this.isResponse = paramBoolean;
    this.videoBitratecapEntity = paramVideoBitratecapEntity;
  }

  public SettingCommand(String paramString, boolean paramBoolean, StartTestUIEntity paramStartTestUIEntity)
  {
    this.action = paramString;
    this.isResponse = paramBoolean;
    this.startTestUIEntity = paramStartTestUIEntity;
  }

  public SettingCommand(String paramString, boolean paramBoolean, StopTestUIEntity paramStopTestUIEntity)
  {
    this.action = paramString;
    this.isResponse = paramBoolean;
    this.stopTestUIEntity = paramStopTestUIEntity;
  }

  private StringBuilder encodeGetResolutionMsgData(boolean paramBoolean, VideoResolutionEntity paramVideoResolutionEntity)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (null == paramVideoResolutionEntity)
      return null;
    if (paramBoolean)
    {
      localStringBuilder.append("resolution").append("=").append(paramVideoResolutionEntity.getResolution());
      localStringBuilder.append("&");
      localStringBuilder.append("current").append("=").append(paramVideoResolutionEntity.getCurrent());
      localStringBuilder.append("&");
      localStringBuilder.append("result").append("=").append(paramVideoResolutionEntity.getResult());
    }
    return localStringBuilder;
  }

  private StringBuilder encodeSetResolutionMsgData(boolean paramBoolean, VideoResolutionEntity paramVideoResolutionEntity)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (null == paramVideoResolutionEntity)
      return null;
    localStringBuilder.append("resolution").append("=").append(paramVideoResolutionEntity.getResolution());
    if (paramBoolean)
    {
      localStringBuilder.append("&");
      localStringBuilder.append("result").append("=").append(paramVideoResolutionEntity.getResult());
    }
    return localStringBuilder;
  }

  private VideoResolutionEntity decodeVideoResolutionMsgData(Map<String, String> paramMap)
  {
    if ((null == paramMap) || (paramMap.size() == 0))
      return null;
    VideoResolutionEntity localVideoResolutionEntity = new VideoResolutionEntity();
    localVideoResolutionEntity.setResolution((String)paramMap.get("resolution"));
    localVideoResolutionEntity.setCurrent((String)paramMap.get("current"));
    localVideoResolutionEntity.setResult((String)paramMap.get("result"));
    return localVideoResolutionEntity;
  }

  private StringBuilder encodeGetFrameratecapMsgData(boolean paramBoolean, VideoFrameratecapEntity paramVideoFrameratecapEntity)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (null == paramVideoFrameratecapEntity)
      return null;
    if (paramBoolean)
    {
      localStringBuilder.append("framerate").append("=").append(paramVideoFrameratecapEntity.getFramerate());
      localStringBuilder.append("&");
      localStringBuilder.append("current").append("=").append(paramVideoFrameratecapEntity.getCurrent());
      localStringBuilder.append("&");
      localStringBuilder.append("result").append("=").append(paramVideoFrameratecapEntity.getResult());
    }
    return localStringBuilder;
  }

  private StringBuilder encodeSetFrameratecapMsgData(boolean paramBoolean, VideoFrameratecapEntity paramVideoFrameratecapEntity)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (null == paramVideoFrameratecapEntity)
      return null;
    localStringBuilder.append("framerate").append("=").append(paramVideoFrameratecapEntity.getFramerate());
    if (paramBoolean)
    {
      localStringBuilder.append("&");
      localStringBuilder.append("result").append("=").append(paramVideoFrameratecapEntity.getResult());
    }
    return localStringBuilder;
  }

  private VideoFrameratecapEntity decodeVideoFrameratecapMsgData(Map<String, String> paramMap)
  {
    if ((null == paramMap) || (paramMap.size() == 0))
      return null;
    VideoFrameratecapEntity localVideoFrameratecapEntity = new VideoFrameratecapEntity();
    localVideoFrameratecapEntity.setFramerate((String)paramMap.get("framerate"));
    localVideoFrameratecapEntity.setCurrent((String)paramMap.get("current"));
    localVideoFrameratecapEntity.setResult((String)paramMap.get("result"));
    return localVideoFrameratecapEntity;
  }

  private StringBuilder encodeGetBitratecapMsgData(boolean paramBoolean, VideoBitratecapEntity paramVideoBitratecapEntity)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (null == paramVideoBitratecapEntity)
      return null;
    if (paramBoolean)
    {
      localStringBuilder.append("videobitrate").append("=").append(paramVideoBitratecapEntity.getVideobitrate());
      localStringBuilder.append("&");
      localStringBuilder.append("current").append("=").append(paramVideoBitratecapEntity.getCurrent());
      localStringBuilder.append("&");
      localStringBuilder.append("result").append("=").append(paramVideoBitratecapEntity.getResult());
    }
    return localStringBuilder;
  }

  private StringBuilder encodeSetBitratecapMsgData(boolean paramBoolean, VideoBitratecapEntity paramVideoBitratecapEntity)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (null == paramVideoBitratecapEntity)
      return null;
    localStringBuilder.append("videobitrate").append("=").append(paramVideoBitratecapEntity.getVideobitrate());
    if (paramBoolean)
    {
      localStringBuilder.append("&");
      localStringBuilder.append("result").append("=").append(paramVideoBitratecapEntity.getResult());
    }
    return localStringBuilder;
  }

  private VideoBitratecapEntity decodeVideoBitratecapMsgData(Map<String, String> paramMap)
  {
    if ((null == paramMap) || (paramMap.size() == 0))
      return null;
    VideoBitratecapEntity localVideoBitratecapEntity = new VideoBitratecapEntity();
    localVideoBitratecapEntity.setVideobitrate((String)paramMap.get("videobitrate"));
    localVideoBitratecapEntity.setCurrent((String)paramMap.get("current"));
    localVideoBitratecapEntity.setResult((String)paramMap.get("result"));
    return localVideoBitratecapEntity;
  }

  private StringBuilder encodeStartTestUIMsgData(boolean paramBoolean, StartTestUIEntity paramStartTestUIEntity)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (null == paramStartTestUIEntity)
      return null;
    if (paramBoolean)
      localStringBuilder.append("result").append("=").append(paramStartTestUIEntity.getResult());
    return localStringBuilder;
  }

  private StartTestUIEntity decodeStartTestUIMsgData(Map<String, String> paramMap)
  {
    if ((null == paramMap) || (paramMap.size() == 0))
      return null;
    StartTestUIEntity localStartTestUIEntity = new StartTestUIEntity();
    localStartTestUIEntity.setResult((String)paramMap.get("result"));
    return localStartTestUIEntity;
  }

  private StringBuilder encodeStopTestUIMsgData(boolean paramBoolean, StopTestUIEntity paramStopTestUIEntity)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (null == paramStopTestUIEntity)
      return null;
    if (paramBoolean)
      localStringBuilder.append("result").append("=").append(this.startTestUIEntity.getResult());
    return localStringBuilder;
  }

  private StopTestUIEntity decodeStopTestUIMsgData(Map<String, String> paramMap)
  {
    if ((null == paramMap) || (paramMap.size() == 0))
      return null;
    StopTestUIEntity localStopTestUIEntity = new StopTestUIEntity();
    localStopTestUIEntity.setResult((String)paramMap.get("result"));
    return localStopTestUIEntity;
  }

  public VideoResolutionEntity getVideoResolutionEntity()
  {
    return this.videoResolutionEntity;
  }

  public void setVideoResolutionEntity(VideoResolutionEntity paramVideoResolutionEntity)
  {
    this.videoResolutionEntity = paramVideoResolutionEntity;
  }

  public VideoFrameratecapEntity getVideoFrameratecapEntity()
  {
    return this.videoFrameratecapEntity;
  }

  public void setVideoFrameratecapEntity(VideoFrameratecapEntity paramVideoFrameratecapEntity)
  {
    this.videoFrameratecapEntity = paramVideoFrameratecapEntity;
  }

  public VideoBitratecapEntity getVideoBitratecapEntity()
  {
    return this.videoBitratecapEntity;
  }

  public void setVideoBitratecapEntity(VideoBitratecapEntity paramVideoBitratecapEntity)
  {
    this.videoBitratecapEntity = paramVideoBitratecapEntity;
  }

  public StartTestUIEntity getStartTestUIEntity()
  {
    return this.startTestUIEntity;
  }

  public void setStartTestUIEntity(StartTestUIEntity paramStartTestUIEntity)
  {
    this.startTestUIEntity = paramStartTestUIEntity;
  }

  public StopTestUIEntity getStopTestUIEntity()
  {
    return this.stopTestUIEntity;
  }

  public void setStopTestUIEntity(StopTestUIEntity paramStopTestUIEntity)
  {
    this.stopTestUIEntity = paramStopTestUIEntity;
  }

  public FocusRectEntity getFocusRectEntity()
  {
    return this.mFocusRectEntity;
  }

  public void setFocusRectEntity(FocusRectEntity paramFocusRectEntity)
  {
    this.mFocusRectEntity = paramFocusRectEntity;
  }

  private StringBuilder encodeStartFocusMsgData(boolean paramBoolean, FocusRectEntity paramFocusRectEntity)
  {
    if (null == this.mFocusRectEntity)
      return null;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("x").append("=").append(paramFocusRectEntity.getX());
    localStringBuilder.append("&");
    localStringBuilder.append("y").append("=").append(paramFocusRectEntity.getY());
    localStringBuilder.append("&");
    localStringBuilder.append("w").append("=").append(paramFocusRectEntity.getWidth());
    localStringBuilder.append("&");
    localStringBuilder.append("h").append("=").append(paramFocusRectEntity.getHeight());
    if (paramBoolean)
    {
      localStringBuilder.append("&");
      localStringBuilder.append("result").append("=").append(paramFocusRectEntity.getResult());
    }
    return localStringBuilder;
  }

  private FocusRectEntity decodeStartFocusMsgData(Map<String, String> paramMap)
  {
    if ((null == paramMap) || (paramMap.size() == 0))
      return null;
    FocusRectEntity localFocusRectEntity = new FocusRectEntity();
    localFocusRectEntity.setResult((String)paramMap.get("result"));
    return localFocusRectEntity;
  }

  private StringBuilder encodeStopFocusMsgData(boolean paramBoolean, FocusRectEntity paramFocusRectEntity)
  {
    if (null == this.mFocusRectEntity)
      return null;
    StringBuilder localStringBuilder = new StringBuilder();
    if (paramBoolean)
    {
      localStringBuilder.append("&");
      localStringBuilder.append("result").append("=").append(paramFocusRectEntity.getResult());
    }
    return localStringBuilder;
  }

  private FocusRectEntity decodeStopFocusMsgData(Map<String, String> paramMap)
  {
    if ((null == paramMap) || (paramMap.size() == 0))
      return null;
    FocusRectEntity localFocusRectEntity = new FocusRectEntity();
    localFocusRectEntity.setResult((String)paramMap.get("result"));
    return localFocusRectEntity;
  }

  private StringBuilder encodeMoveFocusMsgData(boolean paramBoolean, FocusRectEntity paramFocusRectEntity)
  {
    if (null == this.mFocusRectEntity)
      return null;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("x").append("=").append(paramFocusRectEntity.getX());
    localStringBuilder.append("&");
    localStringBuilder.append("y").append("=").append(paramFocusRectEntity.getY());
    localStringBuilder.append("&");
    localStringBuilder.append("w").append("=").append(paramFocusRectEntity.getWidth());
    localStringBuilder.append("&");
    localStringBuilder.append("h").append("=").append(paramFocusRectEntity.getHeight());
    if (paramBoolean)
    {
      localStringBuilder.append("&");
      localStringBuilder.append("result").append("=").append(paramFocusRectEntity.getResult());
    }
    return localStringBuilder;
  }

  private FocusRectEntity decodeMoveFocusMsgData(Map<String, String> paramMap)
  {
    if ((null == paramMap) || (paramMap.size() == 0))
      return null;
    FocusRectEntity localFocusRectEntity = new FocusRectEntity();
    localFocusRectEntity.setResult((String)paramMap.get("result"));
    return localFocusRectEntity;
  }
}
