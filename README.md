# DarsJadval
Dars jadvali

## HTML form
```html
<form onsubmit="alert('stop submit'); return false;">
```

```html
<form onsubmit="return customSubmit();">
```

```html
<form onsubmit="event.preventDefault(); return validateForm();">
```

```html
<form onsubmit="customSubmit(); return false;">
```

```html
<form id="form">
    <button onclick="onSubmit(form)"></button>
</form>

<script>
    function onSubmit(form) {
        form.submit()
    }
</script>
```
