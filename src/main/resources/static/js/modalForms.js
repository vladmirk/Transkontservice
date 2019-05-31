var fstModalName = 'mainModal'
var sndModalName = 'secondModal'

var fstModal = {
  name: fstModalName, id: '#' + fstModalName, title: "", formURL: "", formName: "", formPostURL: "", srcIDField: "", srcDescField: "",
  defaultFocusField: ""
}
var sndModal = {
  name: sndModalName, id: '#' + sndModalName, title: "", formURL: "", formName: "", formPostURL: "", srcIDField: "", srcDescField: "",
  defaultFocusField: ""
}
var modals = [fstModal, sndModal]
var curModal;

function bodyId() {
  return curModal.id + 'Body';
}

function initModal() {

  $(bodyId()).load(curModal.formURL, function () {
    show(curModal);
    var titleID = curModal.id + 'TitleText';
    $(titleID).text(curModal.title);
    // $(modalName).modal('handleUpdate');
  });
}


function show(modal) {
  modals.forEach(function (i) {
    if (modal.id != i.id) {
      hide(i);
    }
  })
  // $(modal.id).modal({show: true});
  curModal = modal;
  $(modal.id).modal('show');

}

function hide(modal) {
  if (modal != undefined) {
    $(modal.id).removeClass("in");
    $(".modal-backdrop").remove();
    $(modal.id).modal('hide');
    // $(modal.id).modal('hide');
  }
}

$(document).ready(function () {
  $(document).on('click', '.modalFormTrigger', function () {
    hide(curModal);
    var modalName = $(this).attr('data-modal-name');
    if (fstModalName == modalName) {
      curModal = fstModal
    } else {
      curModal = sndModal;
    }

    curModal.title = $(this).attr('data-modal-title');
    curModal.formURL = $(this).attr('data-href');
    curModal.formName = '#' + $(this).attr('data-modal-form-name');
    curModal.defaultFocusField = '#' + $(this).attr('data-modal-focus-field');
    curModal.formPostURL = $(this).attr('data-post-href');
    curModal.srcIDField = '#' + $(this).attr('data-modal-src-id');
    curModal.srcDescField = '#' + $(this).attr('data-modal-src-desc');
    initModal();
    // curModal = fstModalName;
  });

  $(document).on('shown.bs.modal', '.focusModal', function () {
    if (curModal.defaultFocusField.length > 1) {
      $(curModal.defaultFocusField).trigger('focus')
    }
  })


  function modalFormOkButtonClick(event, url_, form_) {

  }

  $('#modalOKButton').click(function (event) {
    $.ajax({
      type: "POST",
      url: curModal.formPostURL,
      data: $(curModal.formName).serialize(), // serializes the form's elements.
      success:
        function (data) {
          $(curModal.srcDescField).val(data.description);
          $(curModal.srcIDField).val(data.id);
          // $(curModal).modal('hide');
          hide(curModal);
        },
      error: function (e) {
        $(bodyId()).html(e.responseText);
        $(curModal.srcDescField).val('');
        $(curModal.srcIDField).val('');
        console.log("ERROR: ", e);
        // display(e);
      },
      done: function (e) {
        console.log("DONE");
      }
    });
    event.preventDefault(); // avoid to execute the actual submit of the form.
  });
});

function display(data) {
  var json = "<h4>Ajax Response</h4><pre>"
    + JSON.stringify(data, null, 4) + "</pre>";
  alert(json);
  // $('#feedback').html(json);
}