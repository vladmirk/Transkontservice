$(function () {
  $('.suggester').autocomplete({
    serviceUrl: function () {
      var url = $(this).data('href');
      return url
    },
    onSelect: function (suggestion) {
      var id = '#' + $(this).data('id')
      $(id).val(suggestion.data);
    },
    onSearchStart: function (params) {
      var id = '#' + $(this).data('id')
      $(id).val('');
    }
  });
});
