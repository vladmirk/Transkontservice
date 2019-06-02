var modals = [];

function current() {
  if (modals.length > 0) {
    return modals[modals.length - 1];
  } else {
    return undefined;
  }
}

function initModal() {
  var cur = current();
  if (cur == undefined) {
    return;
  }
  $(bodyId()).load(cur.formURL, function () {
    var titleID = cur.id + '-TitleText';
    $(titleID).text(cur.title);
    showLast();
  });
}


function showLast() {
  if (current() != undefined) {
    showByName(current().id);
  }
}

function showByName(name) {
  $(name).modal('show');
}

function hide(aModel) {
  remove(aModel);
  $(aModel.id).modal('hide');
}

function remove(aModal) {
  for (var i = modals.length - 1; i >= 0; i--) {
    if (modals[i].name == aModal.name) {
      modals.splice(i, 1);
    }
  }
}

function addToModals(aModal) {
  remove(aModal);
  modals.push(aModal);
  initModal();
}

$(document).ready(function () {

  $(document).on('click', '.modalFormTrigger', function () {
    var aModal = new Object();
    aModal.name = $(this).attr('data-modal-name');
    aModal.id = '#' + aModal.name;
    aModal.okButton = '#' + aModal.id + '-OK';
    aModal.title = $(this).attr('data-modal-title');
    aModal.formURL = $(this).attr('data-href');
    aModal.formName = '#' + $(this).attr('data-modal-form-name');
    aModal.defaultFocusField = '#' + $(this).attr('data-modal-focus-field');
    aModal.formPostURL = $(this).attr('data-post-href');
    aModal.srcIDField = '#' + $(this).attr('data-modal-src-id');
    aModal.srcDescField = '#' + $(this).attr('data-modal-src-desc');
    addToModals(aModal);
  });

  $(document).on('shown.bs.modal', '.focusModal', function () {
    if (current().defaultFocusField.length > 1) {
      $(current().defaultFocusField).trigger('focus')
    }
  })

  $(document).on('click', ".modal-ok-button", function (event) {
    var idOK = $(this).attr('id');
    idOK = idOK.substring(0, idOK.lastIndexOf('-OK'));
    var cur = undefined;
    for (var i = 0; i < modals.length; i++) {
      if (modals[i].name == idOK) {
        cur = modals[i];
      }
    }
    if (cur == undefined) {
      return;
    }

    $.ajax({
      type: "POST",
      url: cur.formPostURL,
      data: $(cur.formName).serialize(), // serializes the form's elements.
      success:
        function (data) {
          $(cur.srcDescField).val(data.description);
          $(cur.srcIDField).val(data.id);
          // $(curModal).modal('hide');
          hide(cur);
        },
      error: function (e) {
        $(bodyId()).html(e.responseText);
        $(current().srcDescField).val('');
        $(current().srcIDField).val('');
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

function bodyId() {
  return current().id + '-Body';
}

function display(data) {
  var json = "<h4>Ajax Response</h4><pre>"
    + JSON.stringify(data, null, 4) + "</pre>";
  alert(json);
  // $('#feedback').html(json);
}