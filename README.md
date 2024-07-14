# Read_Me_Android
브랜치 생성
git branch 브랜치명

브랜치를 생성하는 명령어는 git branch이며 첫번째 매개변수는 생성하려는 브랜치명이고 두번째는 분기해 나올 브랜치명이다.

$ git branch RB_1.0 master
이 명령어는 master 브랜치에서 RB_1.0이라는 브랜치를 생성한다. 브랜치명에서 사용한 RB는 릴리스 브랜치의 약어이다.

브랜치 삭제
git branch -D (브랜치)

로컬 브랜치를 삭제하려면 아래 명령어를 사용한다.

$ git branch -D utility
Deleted branch utility (was e7f33f9).
브랜치명 변경하기
git branch -m [브랜치명] [새로운 브랜치명]

마스터 브랜치명을 바꾸려면 다음과 같이 한다.

git branch -m master mymaster
브랜치 이동하기(Checkout)
git checkout (브랜치)

현재 master 브랜치에서 gh-pages 브랜치로 이동하려면 checkout 명령어를 사용한다.

git checkout gh-pages
브랜치 생성과 체크아웃
git checkout -b (새로운 브랜치)

브랜치 생성과 체크아웃을 한번에 하려면 git checkout -b (branch이름)을 입력한다.

git checkout -b utility
Switched to a new branch 'utility'
새로운 브랜치가 생성되고 생성된 새로운 브랜치로 체크아웃된다.

브랜치 관리
현재 브랜치 확인하기
git branch
git branch -v

현재 등록된 브랜치를 확인할려면 아래와 같이 한다.

git branch
* master
  gh-pages
등록된 브랜치의 상세한 정보를 볼려면 아래와 같이 한다.

$ git branch -v
* gh-pages e7f33f9 update html files
  master   5c7085b Merge branch 'master' of github.com:mylko72/BalladBest
