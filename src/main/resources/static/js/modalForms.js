// var fstModalName = 'mainModal'
// var sndModalName = 'secondModal'

// var fstModal = {
//   name: fstModalName, id: '#' + fstModalName, title: "", formURL: "", formName: "", formPostURL: "", srcIDField: "", srcDescField: "",
//   defaultFocusField: ""
// }
// var sndModal = {
//   name: sndModalName, id: '#' + sndModalName, title: "", formURL: "", formName: "", formPostURL: "", srcIDField: "", srcDescField: "",
//   defaultFocusField: ""
// }
// var modals = [fstModal, sndModal]
// var curModal;
var prevModal;
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
    // $(modalName).modal('handleUpdate');
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

function hideCurrent() {
  if (modals.length == 0) {
    return;
  }
  var cur = modals.pop();
  // $(modal.id).removeClass("in");
  // $(".modal-backdrop").remove();
  $(cur.id).modal('hide');
  // $(modal.id).modal('hide');
}

$(document).ready(function () {

  $(document).on('click', '.modalFormTrigger', function () {
    // hide(curModal);
    // var modalName = $(this).attr('data-modal-name');
    // if (fstModalName == modalName) {
    //   curModal = fstModal
    // } else {
    //   curModal = sndModal;
    // }
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
    modals.push(aModal);
    initModal();
    // curModal = fstModalName;
  });

  $(document).on('shown.bs.modal', '.focusModal', function () {
    if (current().defaultFocusField.length > 1) {
      $(current().defaultFocusField).trigger('focus')
    }
  })

  $(document).on('hidden.bs.modal', '.modal', function () {
    // var cur = current();
    // if (cur == undefined) {
    //   return;
    // }
    // showLast()
  })


  $(document).on('click', ".modal-ok-button", function (event) {
    $.ajax({
      type: "POST",
      url: current().formPostURL,
      data: $(current().formName).serialize(), // serializes the form's elements.
      success:
        function (data) {
          $(current().srcDescField).val(data.description);
          $(current().srcIDField).val(data.id);
          // $(curModal).modal('hide');
          hideCurrent();
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

function getPrevious() {
  if (current() != undefined) {
    var name = current().id;
    var sec = name.substr(-1);
    if (sec == '1') {
      return undefined;
    }
    var prev = sec - 1;
    return name.substr(0, name.length - 1) + prev;
  }
}

function display(data) {
  var json = "<h4>Ajax Response</h4><pre>"
    + JSON.stringify(data, null, 4) + "</pre>";
  alert(json);
  // $('#feedback').html(json);
}