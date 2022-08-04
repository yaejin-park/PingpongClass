/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';

function StickerBar({ objectName }: any) {
  return (
    <div css={totalContainer}>
      <h1>{objectName}</h1>
    </div>
  );
}

const totalContainer = css`
  width: 233px;
  height: 233px;
  background: #fdfcf3;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  border-radius: 20px;
  box-shadow: 3px 3px 10px -5px;
  cursor: pointer;
`;

export default StickerBar;
