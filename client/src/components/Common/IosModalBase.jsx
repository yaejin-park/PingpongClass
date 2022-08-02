/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import PropTypes from 'prop-types';
import RedEllipse from '../../assets/images/redEllipse.png';
import YellowEllipse from '../../assets/images/yellowEllipse.png';
import GreenEllipse from '../../assets/images/greenEllipse.png';

const IosModalBase = ({ children, width, height }) => {
  return (
    <div css={totalContainer({ width, height })}>
      <div css={YellowBackground} />
      <div css={WhiteBackground}>
        <div css={Header}>
          <img src={RedEllipse} alt="빨간버튼" />
          <img src={YellowEllipse} alt="노란버튼" />
          <img src={GreenEllipse} alt="초록버튼" />
        </div>
        <div css={Body}>{children}</div>
      </div>
    </div>
  );
};

const totalContainer = ({ width, height }) => css`
  width: ${width};
  height: ${height};
`;

const YellowBackground = css`
  position: absolute;
  width: 100%;
  height: 100%;
  background: #fff1bf;
  border: 5px solid #000000;
  border-radius: 30px;
`;

const WhiteBackground = css`
  position: absolute;
  width: 99.5%;
  height: 98%;
  left: 2%;
  background: #ffffff;
  border: 5px solid #000000;
  border-radius: 20px;
`;

const Header = css`
  width: 100.1%;
  height: 3rem;
  display: flex;
  flex-direction: row;
  align-items: center;
  border-bottom: 5px solid #000000;

  img {
    width: 1.5rem;
    margin-left: 0.5rem;
  }
`;

const Body = css`
  width: 100%;
  height: 100% - 3rem;
`;

IosModalBase.propTypes = {
  children: PropTypes.node,
  width: PropTypes.string,
  height: PropTypes.string,
};

IosModalBase.defaultProps = {
  children: null,
  width: '1000px',
  height: '600px',
};

export default IosModalBase;