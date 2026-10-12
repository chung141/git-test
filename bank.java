public class bank {
    public static void main(String[] args) {
        // 1. 공용 계좌 객체 생성
        Account account = new Account();

        // 2. 남편 스레드 객체 생성
        husband husbandThread = new husband();
        // 3. 부인 스레드 객체 생성
        wife wifeThread = new wife();
        // 4. 남편 객체에게 공용 계좌 객체 전달
        husbandThread.setAccount(account);
        // 5. 부인 객체에게 같은 공용 계좌 객체 전달
        wifeThread.setAccount(account);

        // try-catch 사용
        try {
            System.out.printf("초기 잔액: %,d원%n", account.getBalance());
            // 6. 남편 스레드 시작
            husbandThread.start();
            // 7. 부인 스레드 시작
            wifeThread.start();
            // 8. 남편 스레드가 끝날 때까지 main 스레드 대기
            husbandThread.join();
            // 9. 부인 스레드가 끝날 때까지 main 스레드 대기
            wifeThread.join();

            System.out.println("\n===== 최종 출금 결과 =====");
            // 10. 남편 출금 횟수 및 금액 합계 출력
            System.out.printf("남편: %d회, 합계 %,d원%n",
                    husbandThread.getWithdrawalCount(), husbandThread.getTotalWithdrawn());
            // 11. 부인 출금 횟수 및 금액 합계 출력
            System.out.printf("부인: %d회, 합계 %,d원%n",
                    wifeThread.getWithdrawalCount(), wifeThread.getTotalWithdrawn());
            System.out.printf("부부 출금 합계: %,d원%n",
                    husbandThread.getTotalWithdrawn() + wifeThread.getTotalWithdrawn());
            System.out.printf("최종 잔액: %,d원%n", account.getBalance());
        } catch (InterruptedException e) {
            husbandThread.interrupt();
            wifeThread.interrupt();
            Thread.currentThread().interrupt();
            System.err.println("main 스레드가 중단되어 출금을 종료합니다.");
        }
    }
}
