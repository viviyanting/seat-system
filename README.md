# Seat Selection System
## 專案介紹
本系統為一個座位選擇系統，提供員工選擇與釋放座位的功能。
系統採用前後端分離架構，後端使用 Spring Boot，前端使用 Vue.js。

## 系統架構
後端採用分層架構設計：
- 展示層（Controller）
- 業務層（Service）
- 資料層（Repository）
- 共用層（DTO / Response）

前端使用 Vue.js 實作座位互動 UI。

## 技術使用
### Backend
- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Stored Procedure
- Transaction 管理
### Frontend
- Vue 3
- JavaScript
- Fetch API
- CSS Grid
## 專案結構
seat-system

├ backend (Spring Boot API)

├ frontend (Vue UI)

└ README.md

## 功能
- 查看座位表
- 指派座位
- 釋放座位
- 防止重複選位
- 座位佔用檢核

## API
### 取得座位表
GET /api/seats

### 選擇/取消座位
POST /api/seats/action
參數empId、seatSeq

## 執行方式
### Backend
### Backend 設定
請先複製設定檔：
src/main/resources/application-example.yaml
並重新命名為：application.yaml
然後填入自己的資料庫帳號與密碼。

cd backend 
使用IntelliJ執行 SeatSystemApplication


### Frontend
cd frontend
npm install
npm run dev

前端預設運行: http://localhost:5173

後端API: http://localhost:8080

Backend 使用 IntelliJ 執行 SeatSystemApplication。
若第一次啟動專案，請先 Build / Rebuild Project。

### 系統架構圖
Vue UI

↓

Spring Boot Controller

↓

Service (Business Logic)

↓

Repository

↓

MySQL




