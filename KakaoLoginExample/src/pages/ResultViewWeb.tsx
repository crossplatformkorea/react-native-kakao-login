import React from 'react';
import styled from 'styled-components';

interface ResultViewType {
  type?: "login" | "profile" | "logout" | "unlink"
  state: any
}

const ResultView = ({ type, state }: ResultViewType) => {

  console.log(state, 'state');
  return (
    () => {
      switch (type) {
        case "login": {
          return (
            <ResultBox>
              <h1>로그인 토큰</h1>
              <span>{state}</span>
            </ResultBox>
          );
        }
        case "profile": {
          return (
            <ResultBox>
              <h1>프로필</h1>
              <span>{state.nickname}</span>
              <img src={state.profile_image} style={{ width: 100, height: 100 }} />
            </ResultBox>
          );
        }
        case "logout": {
          return (
            <ResultBox>
              <h1>로그아웃 완료</h1>
            </ResultBox>
          );
        }
        case "unlink": {
          return (
            <ResultBox>
              <h1>링크 해제 완료</h1>
            </ResultBox>
          );
        }
        default: {
          return (
            <ResultBox>
              <h1>카카오 로그인</h1>
              <span>{state}</span>
            </ResultBox>
          );
        }
      }
    }
  )();
};

const ResultBox = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-bottom: 10px;
`;

export default ResultView;
