import React, { Component } from 'react';
import './ToolbarComponent.css';

import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';

import Mic from '@mui/icons-material/Mic';
import MicOff from '@mui/icons-material/MicOff';
import Videocam from '@mui/icons-material/Videocam';
import VideocamOff from '@mui/icons-material/VideocamOff';
import Fullscreen from '@mui/icons-material/Fullscreen';
import FullscreenExit from '@mui/icons-material/FullscreenExit';
import SettingsIcon from '@mui/icons-material/Settings';
import PictureInPicture from '@mui/icons-material/PictureInPicture';
import ScreenShare from '@mui/icons-material/ScreenShare';
import StopScreenShare from '@mui/icons-material/StopScreenShare';
import PowerSettingsNew from '@mui/icons-material/PowerSettingsNew';
import QuestionAnswer from '@mui/icons-material/QuestionAnswer';
import PeopleIcon from '@mui/icons-material/People';
import Shuffle from '@mui/icons-material/Shuffle';
import Quiz from '@mui/icons-material/HelpOutline';
import AccessTime from '@mui/icons-material/AccessTime';
import IconButton from '@mui/material/IconButton';
import ViewAgenda from '@mui/icons-material/ViewAgenda';
import ViewArray from '@mui/icons-material/ViewArray';
import Share from '@mui/icons-material/Share';
import SearchIcon from '@mui/icons-material/Search';
import EmojiEmotionsIcon from '@mui/icons-material/EmojiEmotions';
import AutoAwesomeMotionIcon from '@mui/icons-material/AutoAwesomeMotion';
import TeachersToolbar from './TeachersToolbar';

export default class ToolbarComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      fullscreen: false,
      randAvailable: true,
      stickerAvailable: true,
    };
    this.camStatusChanged = this.camStatusChanged.bind(this);
    this.micStatusChanged = this.micStatusChanged.bind(this);
    this.screenShare = this.screenShare.bind(this);
    this.stopScreenShare = this.stopScreenShare.bind(this);
    this.toggleFullscreen = this.toggleFullscreen.bind(this);
    this.leaveSession = this.leaveSession.bind(this);
    this.toggleChat = this.toggleChat.bind(this);
    this.toggleParticipant = this.toggleParticipant.bind(this);
    this.toggleQuiz = this.toggleQuiz.bind(this);
    this.pickRandomStudent = this.pickRandomStudent.bind(this);
    this.startStickerEvent = this.startStickerEvent.bind(this);
    this.toggleSetting = this.toggleSetting.bind(this);
    this.selfLeaveSession = this.selfLeaveSession.bind(this);
    this.toggleVideoLayout = this.toggleVideoLayout.bind(this);
    this.toggleEmoji = this.toggleEmoji.bind(this);
    this.toggleQuestion = this.toggleQuestion.bind(this);
    this.toggleTeacherMenu = this.toggleTeacherMenu.bind(this);
  }

  // micStatusChanged: ????????? ???????????? ?????? ??????
  micStatusChanged() {
    this.props.micStatusChanged();
  }

  // camStatusChanged: ??? ???????????? ?????? ??????
  camStatusChanged() {
    this.props.camStatusChanged();
  }

  // screenShare: ??????????????? ?????? ??????
  screenShare() {
    this.props.screenShare();
  }

  // screenShare: ??????????????? ?????? ??????
  stopScreenShare() {
    this.props.stopScreenShare();
  }

  // toggleFullscreen: ???????????? ?????? ??????
  toggleFullscreen() {
    this.setState({ fullscreen: !this.state.fullscreen });
    this.props.toggleFullscreen();
  }

  // leaveSession: ?????? ?????? ??????
  leaveSession() {
    this.props.leaveSession();
  }

  // selfLeaveSession: ?????? ?????? ?????? ??????
  selfLeaveSession() {
    this.props.selfLeaveSession();
  }

  // toggleChat: ?????? ?????? ??????
  toggleChat() {
    this.props.toggleChat();
  }

  toggleSetting() {
    this.props.toggleSetting();
  }

  toggleParticipant() {
    this.props.toggleParticipant();
  }

  toggleEmoji() {
    this.props.toggleEmoji();
  }

  toggleQuestion() {
    this.props.toggleQuestion();
  }

  // name: ?????????
  // date: 2022/07/25
  // desc: ???????????? ????????? ????????? ???????????? ??????
  // todo: ??? Subscribers ??? ????????? 1?????? ????????? ????????????, ???????????? ????????? ???????????? 1.5????????? ??????????????? ?????? ?????????, 3??? ?????? ?????? ????????? ???????????? ?????????.
  pickRandomStudent() {
    this.lockOut(6);
    this.props.pickRandomStudent(this.props.subscribers, false);
  }

  // name: ?????????
  // date: 2022/07/27
  // desc: ?????? ?????? ????????? ?????? ???????????? ???????????? ???????????? ??????
  // Todo: ?????? ??? ?????? ????????? ????????? ???????????? disabled ????????? ??????
  lockOut(lockOutTime) {
    this.setState({ randAvailable: false });
    setTimeout(() => {
      this.setState({ randAvailable: true });
    }, lockOutTime * 1000);
  }

  toggleQuiz() {
    this.props.toggleQuiz();
  }

  // lockOutSticker: ?????? ??? ??????????????? ????????? ????????? ?????? (30???) ?????? disabled ????????? ??????
  lockOutSticker(lockOutTime) {
    this.setState({ stickerAvailable: false });
    setTimeout(() => {
      this.setState({ stickerAvailable: true });
    }, lockOutTime * 1000);
  }

  startStickerEvent() {
    this.props.startStickerEvent();
    this.lockOutSticker(31);
  }

  toggleTeacherMenu() {
    this.props.toggleTeacherMenu();
  }

  // name: ?????????
  // date: 2022/08/13
  // desc: ??? ????????? ????????? ??????????????? ??????????????? ??????.
  // todo: ?????? ????????? ???????????? ????????? ??????????????? ?????? ????????? ???????????????.
  toggleVideoLayout() {
    this.props.toggleVideoLayout();
  }

  VideoLayout = () => {
    if (this.props.videoLayout === 'bigTeacher') {
      return (
        <div className="buttonStyle">
          <ViewAgenda />
          <p>????????? ??????</p>
        </div>
      );
    } else if (this.props.videoLayout === 'bigTeacher') {
      return (
        <div className="buttonStyle">
          <ViewArray />
          <p>????????????</p>
        </div>
      );
    } else if (this.props.videoLayout === 'screenShareOn') {
      return (
        <div className="buttonStyle">
          <ScreenShare />
          <p>????????????</p>
        </div>
      );
    } else {
      return null;
    }
  };

  // render: ????????? ??????
  render() {
    // const mySessionId = this.props.sessionId;
    const localUser = this.props.user;
    return (
      <AppBar
        className="toolbar"
        id={this.props.whoami === 'teacher' ? 'teacher-header' : 'header'}
      >
        <Toolbar className="toolbar">
          {this.props.sessionId && (
            <div id="titleContent">
              {/* <span id="session-title">{mySessionId}</span> */}
              <span id="session-title">
                {this.props.classTitle} - {this.props.teacherName}
              </span>
            </div>
          )}

          <div className="buttonsContent">
            <IconButton
              color="inherit"
              className="navButton"
              id="navMicButton"
              onClick={this.micStatusChanged}
            >
              {localUser !== undefined && localUser.isAudioActive() ? (
                <div className="buttonStyle">
                  <Mic />
                  <p>?????????</p>
                </div>
              ) : (
                <div className="buttonStyle">
                  <MicOff color="secondary" />
                  <p>????????? ??????</p>
                </div>
              )}
            </IconButton>

            <IconButton
              color="inherit"
              className="navButton"
              id="navCamButton"
              onClick={this.camStatusChanged}
            >
              {localUser !== undefined && localUser.isVideoActive() ? (
                <div className="buttonStyle">
                  <Videocam />
                  <p>????????? ??????</p>
                </div>
              ) : (
                <div className="buttonStyle">
                  <VideocamOff color="secondary" />
                  <p>????????? ??????</p>
                </div>
              )}
            </IconButton>

            {this.props.whoami === 'teacher' && (
              <IconButton
                color="inherit"
                className="navButton"
                id="navRandButton"
                onClick={this.toggleTeacherMenu}
              >
                <div className="buttonStyle">
                  <AutoAwesomeMotionIcon />
                  <p>????????? ??????</p>
                </div>
              </IconButton>
            )}

            {this.props.whoami === 'teacher' && (
              <div className="teacher-toolbar">
                <TeachersToolbar
                  display={this.props.teacherMenuDisplay}
                  randAvailable={this.state.randAvailable}
                  stickerAvailable={this.state.stickerAvailable}
                  pickRandomStudent={this.pickRandomStudent}
                  startStickerEvent={this.startStickerEvent}
                  toggleQuiz={this.toggleQuiz}
                  toggleTeacherMenu={this.toggleTeacherMenu}
                />
              </div>
            )}

            <IconButton
              color="inherit"
              className="navButton"
              onClick={this.screenShare}
            >
              {localUser !== undefined && localUser.isScreenShareActive() ? (
                <div className="buttonStyle">
                  <PictureInPicture />
                  <p>???????????? ??????</p>
                </div>
              ) : (
                <div className="buttonStyle">
                  <ScreenShare />
                  <p>????????????</p>
                </div>
              )}
            </IconButton>

            {localUser !== undefined && localUser.isScreenShareActive() && (
              <IconButton onClick={this.stopScreenShare} id="navScreenButton">
                <div className="buttonStyle">
                  <StopScreenShare color="secondary" />
                  <p>???????????? ??????</p>
                </div>
              </IconButton>
            )}

            <IconButton
              color="inherit"
              className="navButton"
              onClick={this.toggleSetting}
            >
              <div className="buttonStyle">
                <SettingsIcon />
                <p>??????</p>
              </div>
            </IconButton>

            <IconButton
              color="inherit"
              className="navButton"
              onClick={this.toggleFullscreen}
            >
              {localUser !== undefined && this.state.fullscreen ? (
                <div className="buttonStyle">
                  <FullscreenExit />
                  <p>???????????? ??????</p>
                </div>
              ) : (
                <div className="buttonStyle">
                  <Fullscreen />
                  <p>????????????</p>
                </div>
              )}
            </IconButton>

            <IconButton
              color="inherit"
              className="navButton"
              onClick={this.toggleVideoLayout}
            >
              {localUser !== undefined
                ? (this.props.videoLayout === 'bigTeacher' && (
                    <div className="buttonStyle">
                      <ViewAgenda />
                      <p>????????? ??????</p>
                    </div>
                  )) ||
                  (this.props.videoLayout === 'equalSize' && (
                    <div className="buttonStyle">
                      <ViewArray />
                      <p>????????????</p>
                    </div>
                  )) ||
                  (this.props.videoLayout === 'screenShareOn' && (
                    <div className="buttonStyle">
                      <Share />
                      <p>????????? ??????</p>
                    </div>
                  ))
                : null}
            </IconButton>

            {this.props.whoami !== 'teacher' ? (
              <IconButton
                color="secondary"
                className="navButton"
                onClick={this.selfLeaveSession}
                id="navLeaveButton"
              >
                <div className="buttonStyle">
                  <PowerSettingsNew />
                  <p>?????? ?????????</p>
                </div>
              </IconButton>
            ) : (
              <IconButton
                color="secondary"
                className="navButton"
                onClick={this.leaveSession}
                id="navLeaveButton"
              >
                <div className="buttonStyle">
                  <PowerSettingsNew />
                  <p>?????? ?????????</p>
                </div>
              </IconButton>
            )}

            <IconButton
              color="inherit"
              onClick={this.toggleEmoji}
              className="navButton"
              id="navEmoji"
            >
              <div className="buttonStyle">
                <EmojiEmotionsIcon />
                <p>?????????</p>
              </div>
            </IconButton>

            <IconButton
              color="inherit"
              onClick={this.toggleQuestion}
              className="navButton"
              id="navQuestButton"
            >
              <div className="buttonStyle">
                {this.props.showQuestionNotification && (
                  <div id="questPoint" className="" />
                )}
                <SearchIcon />
                <p>????????????</p>
              </div>
            </IconButton>

            <IconButton
              color="inherit"
              onClick={this.toggleParticipant}
              className="navButton"
              id="navParticipantButton"
            >
              <div className="buttonStyle">
                <PeopleIcon />
                <p>????????? ??????</p>
              </div>
            </IconButton>

            <IconButton
              color="inherit"
              onClick={this.toggleChat}
              className="navButton"
              id="navChatButton"
            >
              <div className="buttonStyle">
                {this.props.showNotification && <div id="point" className="" />}
                <QuestionAnswer />
                <p>??????</p>
              </div>
            </IconButton>
          </div>
        </Toolbar>
      </AppBar>
    );
  }
}
