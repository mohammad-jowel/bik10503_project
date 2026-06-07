const slides = Array.from(document.querySelectorAll('.slide'));
const counter = document.getElementById('counter');
const dots = document.getElementById('dots');
const nextBtn = document.getElementById('nextBtn');
const prevBtn = document.getElementById('prevBtn');
let current = 0;

slides.forEach((_, index) => {
  const dot = document.createElement('button');
  dot.className = 'dot';
  dot.type = 'button';
  dot.setAttribute('aria-label', `Go to slide ${index + 1}`);
  dot.addEventListener('click', () => goTo(index));
  dots.appendChild(dot);
});

function refreshAnimations(slide) {
  slide.querySelectorAll('.reveal').forEach(el => {
    el.style.animation = 'none';
    void el.offsetHeight;
    el.style.animation = '';
  });
}

function goTo(index) {
  if (index < 0 || index >= slides.length) return;
  slides[current].classList.remove('active');
  current = index;
  slides[current].classList.add('active');
  refreshAnimations(slides[current]);
  updateUI();
}

function updateUI() {
  const number = String(current + 1).padStart(2, '0');
  const total = String(slides.length).padStart(2, '0');
  counter.textContent = `${number} / ${total}`;
  document.querySelectorAll('.dot').forEach((dot, i) => dot.classList.toggle('active', i === current));
  document.title = `${number} · ${slides[current].dataset.title} | BIK10503`;
}

function toggleFullscreen() {
  if (!document.fullscreenElement) document.documentElement.requestFullscreen?.();
  else document.exitFullscreen?.();
}

nextBtn.addEventListener('click', () => goTo(current + 1));
prevBtn.addEventListener('click', () => goTo(current - 1));

document.addEventListener('keydown', event => {
  const key = event.key.toLowerCase();
  if (key === 'arrowright' || key === ' ') goTo(current + 1);
  if (key === 'arrowleft') goTo(current - 1);
  if (key === 'home') goTo(0);
  if (key === 'end') goTo(slides.length - 1);
  if (key === 'f') toggleFullscreen();
  if (key === 'p') document.body.classList.toggle('hide-cues');
});

updateUI();
