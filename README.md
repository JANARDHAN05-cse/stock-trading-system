SQL Use Case:
List top traders by profit and most traded stocks.

SQL Queries:
1. Top Traders by Profit
SELECT trader_id, SUM(profit_loss) AS profit
FROM Trades
GROUP BY trader_id
ORDER BY profit DESC;


2. Most Traded Stocks
SELECT stock_id, SUM(quantity) AS traded
FROM Trades
GROUP BY stock_id
ORDER BY traded DESC;
