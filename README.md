# Exchange Rate Calculator

The REST service receives three parameters: two currencies and a date using the
URL path. The format of the URL path is


localhost:8080/api/exchange-rate/{date}/{baseCurrency}/{targetCurrency}

The REST service returns:
- The exchange rate of the requested date.
- The average of the five days before the requested date (excluding Saturday and
Sunday )
- Exchange rate trend.

### The exchange rate trend is determined using following definition:

- **descending:** when the exchange rates in the last five days are in strictly
descending order,
- **ascending:** when the exchange rates in the last five days are in strictly ascending
order
- **constant:** when the exchange rates in the last five days are the same
- **undefined:** in other cases.
	
### Daily and Monthly information
Using two API’s for the historical information, one for the daily information and other for
the monthly information.
- daily: localhost:8080/api/exchange-rate/history/daily/{yyyy}/{MM}/{dd}
- monthly: localhost:8080/api/exchange-rate/history/monthly/{yyyy}/{MM}



### Unit and Integration tests were implemented.


