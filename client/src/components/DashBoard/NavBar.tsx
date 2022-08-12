import { css } from '@emotion/react';
import mainLogo from '@assets/images/mainLogo.png';
import DashboardIcon from '@mui/icons-material/Dashboard';
import CalendarMonthIcon from '@mui/icons-material/CalendarMonth';
import CampaignIcon from '@mui/icons-material/Campaign';
import StorefrontIcon from '@mui/icons-material/Storefront';
import { Link } from 'react-router-dom';

const NavBar = () => {
  return (
    <div css={totalContainer}>
      <Link to="" className="linkButton">
        <img src={mainLogo} alt="" />
      </Link>
      <div className="dashNavButton">
        <DashboardIcon className="nav-div" />
        <h4>
          <Link to="" className="linkButton">
            대시보드
          </Link>
        </h4>
      </div>
      <div className="dashNavButton">
        <CalendarMonthIcon className="nav-div" />
        <h4>
          <Link to="classes" className="linkButton">
            수업목록
          </Link>
        </h4>
      </div>
      <div className="dashNavButton">
        <CampaignIcon className="nav-div" />
        <h4>
          <Link to="notice" className="linkButton">
            공지사항
          </Link>
        </h4>
      </div>
      <div className="dashNavButton">
        <StorefrontIcon className="nav-div" />
        <h4>
          <Link to="store" className="linkButton">
            상점
          </Link>
        </h4>
      </div>
    </div>
  );
};

const totalContainer = css`
  width: 210px;
  background: rgba(255, 255, 255, 0.9);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  border-radius: 20px;
  gap: 19px;
  padding: 30px 10px;
  margin-top: 50px;
  box-shadow: 2px 2px 15px -5px;
  .nav-div {
    font-size: 30px;
  }

  .dashNavButton {
    width: 80%;
    height: 40px;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: space-between;
    font-size: calc(0.3rem + 1vw);
    padding-left: 18px;
    cursor: pointer;
    box-sizing: border-box;
    transition: all 0.1s ease-in-out;
  }

  .dashNavButton:hover {
    transform: scale(1.1);
  }

  .dashNavButton h4 {
    font-size: 20px;
    width: 100%;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
  }

  .linkButton {
    text-decoration: none;
    color: black;
  }

  img {
    width: 90%;
    margin: 19px 8px;
  }
`;

export default NavBar;
