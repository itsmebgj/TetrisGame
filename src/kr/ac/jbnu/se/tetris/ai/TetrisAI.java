package kr.ac.jbnu.se.tetris.ai;

import java.util.LinkedList;
import java.util.Queue;

import kr.ac.jbnu.se.tetris.board.BoardAI;
import kr.ac.jbnu.se.tetris.shape.Shape;
import kr.ac.jbnu.se.tetris.shape.Tetrominoes;

public class TetrisAI {

    private BoardAI board;

    private Queue<Shape> shapeQueue;
    private Queue<String> routeQueue;

    private boolean[][][] visited;
    
    private Shape curPiece;

    private StringBuilder bestRoute;

    private int maxWeight;
    
    private int[] dx = { -1, 1, 0, 0 };
    private int[] dy = { 0, 0, -1, 1 };
    
    public TetrisAI(BoardAI board) {
        this.board = board;
        bestRoute = new StringBuilder();
    }

    /**
     * 실제로 외부에 제공되는 연산
     * 
     * 테트리스 블록의 최적 루트를 탐색
     * 
     * @return 0, 1, 2, 3 으로 이루어져 있는 문자열
     *          0 : 왼쪽 이동, 1 : 오른쪽 이동, 2 : 아래 이동, 3 : 시계방향 회전
     */
    public String getBestRoute() {
        initFindRoute();
        while(!shapeQueue.isEmpty()) {
            try {
                Shape piece = (Shape) shapeQueue.poll().clone();
                String curRoute = routeQueue.poll();

                findRouteMove(piece, curRoute);
                fineRouteRotate(piece, curRoute);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            } 
        }
        return bestRoute.toString();
    }

    /**
     * 루트 탐색의 초기 세팅
     */
    private void initFindRoute() {
        shapeQueue = new LinkedList<>();
        routeQueue = new LinkedList<>();
        
        maxWeight = -10;
        bestRoute.setLength(0);
        visited = new boolean[board.height()][board.width()][4];

        try {
            curPiece = (Shape) board.getCurPiece().clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < 4; i++){
            visited[curPiece.curY()][curPiece.curX()][curPiece.getRotateIndex()] = true;

            routeQueue.add(bestRoute.toString());
            bestRoute.append("3");
            
            shapeQueue.add(curPiece);
            curPiece = curPiece.rotateRight();
        }
    }

    /**
     * 현재 블록의 위치에서 좌측, 우측, 하단으로의 이동했을 때의 루트를 저장하는 메소드
     *      -> curRoute + ('0' or '1' or '2').
     * 
     * 더이상 움직일 수 없는 경우 현재 위치에서 가중치를 계산한다.
     * 
     * 계산된 가중치가 기존보다 더 크면, maxWeight, bestRoute 값을 갱신한다.
     * 
     * @param piece 현재 블록의 위상 (위치, 형태)
     * @param curRoute 현재까지 이동한 위치
     */
    private void findRouteMove(Shape piece, String curRoute) {
        int curX = piece.curX();
        int curY = piece.curY();
        for(int i = 0; i < 3; i++) {
            try {
                Shape nPiece = (Shape) piece.clone();
                int nX = curX + dx[i];
                int nY = curY + dy[i];
                if((nX < 0 || nX >= board.width() || nY < 0 || nY >= board.height()) ||
                        visited[nY][nX][nPiece.getRotateIndex()])
                    continue;
                if(!board.tryMove(nPiece, nX, nY)){
                    int weight = getWeight(nPiece);
                    if(maxWeight <= weight){
                        bestRoute.setLength(0);
                        bestRoute.append(curRoute);
                        maxWeight = weight;
                    }
                }
                else{
                    nPiece.moveTo(nX, nY);
                    visited[nY][nX][nPiece.getRotateIndex()] = true;
                    routeQueue.add(curRoute + Integer.toString(i));
                    shapeQueue.add(nPiece);
                }
            } catch (CloneNotSupportedException e) {
                return;
            }
        }
    }

    /**
     * 현재 블록의 위치에서 시계 방향으로 3번 회전시키고, 각각의 루트를 저장하는 메소드
     *      -> curRoute + '3'.
     * 
     * 더이상 움직일 수 없는 경우 현재 위치에서 가중치를 계산한다.
     * 
     * 계산된 가중치가 기존보다 더 크면, maxWeight, bestRoute 값을 갱신한다.
     * 
     * @param piece 현재 블록의 위상 (위치, 형태)
     * @param curRoute 현재까지 이동한 위치
     */
    private void fineRouteRotate(Shape piece, String curRoute) {
        int curX = piece.curX();
        int curY = piece.curY();
        Shape rPiece = piece;
        for(int i = 0; i < 3; i++) {
            rPiece = rPiece.rotateRight();
            if(visited[curY][curX][rPiece.getRotateIndex()])
                continue;
            if(!board.tryMove(rPiece, curX, curY)){
                rPiece = rPiece.rotateLeft();
                int weight = getWeight(rPiece);
                if(maxWeight < weight){
                    bestRoute.setLength(0);
                    bestRoute.append(curRoute);
                    maxWeight = weight;
                }
                break;
            }
            visited[curY][curX][rPiece.getRotateIndex()] = true;
            routeQueue.add(curRoute + "3");
            shapeQueue.add(rPiece);
        }
    }

    /**
     * 현재 블록의 위치에서 가중치를 계산하는 메소드
     * 
     * 현재 블록 주변에 있는 다른 블록의 수를 합산하여 가중치를 계산한다.
     * 
     * @param piece 가중치를 계산할 대상 블록
     * @return 계산된 가중치를 return 한다.
     */
    private int getWeight(Shape piece) {
        boolean[][] isCurPiece = new boolean[board.height()][board.width()];
        
        for(int i = 0; i < 4; i++){
            int blockX = piece.curX() + piece.x(i);
            int blockY = piece.curY() - piece.y(i);            
            isCurPiece[blockY][blockX] = true;
        }

        int weight = 0;

        for(int i = 0; i < 4; i++){
            int blockX = piece.curX() + piece.x(i);
            int blockY = piece.curY() - piece.y(i);
            
            for(int j = 0; j < 4; j++){ 
                int x = blockX + dx[j];
                int y = blockY + dy[j];

                if(x + 1 == 0 || x == board.width() || y + 1 == 0 || y == board.height()) {
                    weight++;
                    continue;
                }

                if(board.shapeAt(x, y) == Tetrominoes.NO_SHAPE) {
                    weight--;
                }
                else if(!isCurPiece[y][x] && board.shapeAt(x, y) != Tetrominoes.NO_SHAPE) {
                    weight++;
                }
            }
        }
        
        return weight;
    }
}